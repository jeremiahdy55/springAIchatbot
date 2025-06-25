package com.hotelchatbot.openai;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelchatbot.domain.Hotel;
import com.hotelchatbot.domain.HotelRoom;
import com.hotelchatbot.embeddings.EmbeddingService;
import com.hotelchatbot.embeddings.StopWords;
import com.hotelchatbot.service.HotelEmbeddingService;
import com.hotelchatbot.service.HotelRoomEmbeddingService;

@Service
public class ChatService {

    @Autowired
    EmbeddingService embeddingService;

    @Autowired
    HotelEmbeddingService hotelEmbeddingService;

    @Autowired
    HotelRoomEmbeddingService hotelRoomEmbeddingService;

    private final ChatModel chatModel;
    private final ChatMemory chatMemory;
    private final String initChatbotMessage = """
                        You are a helpful assistant answering hotel search queries using Hotel or HotelRoom JSON data. \
                        Hotel includes: name, price, location, contact info, amenities, and description. \
                        HotelRoom includes: available room count, pricing, policies, amenities, and description. \
                        Always include the hotel name when referring to either entity. \
                        If you cannot confidently answer the query with the data, state your confidence clearly. \
                        If no JSON data was provided, do not invent information; instead provide a generic response and prompt the user again.
                        """;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
        // this.chatMemory = MessageWindowChatMemory.builder().maxMessages(15).build();
        // default maxMessages = 20; remember, each individual Message counts! 
        // (i.e. SystemMessage, UserMessage, AssistantMessage)
        this.chatMemory = MessageWindowChatMemory.builder().build();
    }

    // This function uses the ChatModel to determine whether the user
    // is asking about hotel rooms or hotels themselves.
    public String determineEntityForSemanticSearch(String userQuery) {
        // ChatClient chatClient = chatClientBuilder.build();
        List<Message> messages = new ArrayList<>();
        String queryContext = """
                You are a helpful assistant that classifies user queries as related to either the \
                Hotel or HotelRoom entity. Hotel includes: name, price, location, contact info, amenities, \
                and description. HotelRoom refers to a room type within a hotel, including: room count, pricing, \
                policies, amenities, and description. Respond only with \"Hotel\" or \"HotelRoom\". \
                If the user mentions wanting to book a room, then return \"Booking\". \
                If the user query is determined to not be asking about Hotel or HotelRoom entities, then return \"Generic\".""";
        messages.add(new SystemMessage(queryContext));
        messages.add(new UserMessage(StopWords.removeStopWords(userQuery)));
        String response = chatModel.call(new Prompt(messages)).getResult().getOutput().getText();
        System.out.println(response);
        // If the response isn't "Hotel" or "HotelRoom", return ""
        if (response == null
                || !(
                    response.equals("Hotel")
                        || response.equals("HotelRoom")
                        || response.equals("Generic")
                        || response.equals("Booking")
                    )) {
            return "";
        } else {
            return response;
        }
    }

    public String generateResponse(String conversationId, String input) {

        String systemMessageHeader = "Please use the provided entity data to formulate your response.\n";
        String userQuery = StopWords.removeStopWords(input); // remove filler words
        String returnText = null;
        // If this is the first message from the user, initialize the chatbot
        if (chatMemory.get(conversationId).isEmpty()) {
            chatMemory.add(conversationId, new SystemMessage(initChatbotMessage));
        }

        // Determine the table to semantic search (cosine similarity with vector
        // embeddings of the data)
        int attemptNo = 0;
        String entityToSearch = "";
        while (attemptNo < 3 && entityToSearch.equals("")) {
            entityToSearch = determineEntityForSemanticSearch(userQuery);
            attemptNo++;
        }
        // System.out.println("This is the entity to search " + entityToSearch);
        // System.out.println(new Prompt(chatMemory.get(conversationId)));

        if (entityToSearch.equals("Generic")) {
            System.out.println("userQuery classified as type: Generic!");
            chatMemory.add(conversationId, new UserMessage(userQuery));
            ChatResponse response = chatModel.call(new Prompt(chatMemory.get(conversationId)));
            returnText = response.getResult().getOutput().getText();
        } else if (entityToSearch.equals("Booking")) {
            System.out.println("userQuery classified as type: Booking!");
            // Implement booking response later, need to do frontend first
            // chatMemory.add(conversationId, new UserMessage(userQuery));
            // ChatResponse response = chatModel.call(new Prompt(chatMemory.get(conversationId)));
            // returnText = response.getResult().getOutput().getText();
        } else {

            try {
                float[] userQueryEmbedding = embeddingService.getEmbedding(userQuery);
                switch (entityToSearch) {
                    case "Hotel" -> {
                        System.out.println("userQuery classified as type: Hotel!");
                        List<Hotel> hotels = hotelEmbeddingService.findTop3BySimilarity(userQueryEmbedding);
                        String hotelData = hotels.stream().map(Hotel::toJsonObjectString)
                                .collect(Collectors.joining("\n"));
                        // System.out.println(systemMessageHeader + hotelData);
                        SystemMessage hotelContextMessage = new SystemMessage(systemMessageHeader + hotelData);
                        chatMemory.add(conversationId, hotelContextMessage);
                        chatMemory.add(conversationId, new UserMessage(userQuery));
                    }

                    case "HotelRoom" -> {
                        System.out.println("userQuery classified as type: HotelRoom!");
                        List<HotelRoom> rooms = hotelRoomEmbeddingService.findTop5BySimilarity(userQueryEmbedding);
                        String roomData = rooms.stream().map(HotelRoom::toJsonObjectString)
                                .collect(Collectors.joining("\n"));
                        // System.out.println(systemMessageHeader + roomData);
                        SystemMessage hotelRoomContextMessage = new SystemMessage(systemMessageHeader + roomData);
                        chatMemory.add(conversationId, hotelRoomContextMessage);
                        chatMemory.add(conversationId, new UserMessage(userQuery));
                    }

                    default -> System.out.println("User query is not about Hotel or HotelRoom! Break!");
                }

                ChatResponse response = chatModel.call(new Prompt(chatMemory.get(conversationId)));
                returnText = response.getResult().getOutput().getText();
                
            } catch (IOException err) {
                err.printStackTrace();
                System.out.println("Error thrown in ChatService.generateResponse()!");
            }
        }
        chatMemory.add(conversationId, new AssistantMessage(returnText));
        return returnText;
    }

}
