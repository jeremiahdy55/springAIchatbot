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
                        Hotel includes: name, price, location, contact info, amenities, description, and guest capacity. \
                        HotelRoom includes: room-specific type, pricing, policies, amenities, available dates, and number of open rooms for this type.  \
                        Always include the hotel name when referring to either entity. \
                        Never include hotelId or hotelRoomId in regular responses. \
                        If you cannot confidently answer the query with the data, use an appropriate amount of confidence in constructing your response. \
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

        // Parse the information and generate a text response
        try {
            float[] userQueryEmbedding = embeddingService.getEmbedding(userQuery);
            switch (entityToSearch) {
                case "Generic" -> {
                    System.out.println("userQuery classified as type: " + entityToSearch);
                    chatMemory.add(conversationId, new UserMessage(userQuery));
                    ChatResponse response = chatModel.call(new Prompt(chatMemory.get(conversationId)));
                    returnText = response.getResult().getOutput().getText();
                }
        
                case "Hotel" -> {
                    System.out.println("userQuery classified as type: Hotel!");
                    List<Hotel> hotels = hotelEmbeddingService.findTop3BySimilarity(userQueryEmbedding);
                    String hotelData = hotels.stream().map(Hotel::toJsonObjectString)
                            .collect(Collectors.joining("\n"));
                    SystemMessage hotelContextMessage = new SystemMessage(systemMessageHeader + hotelData);
                    chatMemory.add(conversationId, hotelContextMessage);
                    chatMemory.add(conversationId, new UserMessage(userQuery));
                    ChatResponse response = chatModel.call(new Prompt(chatMemory.get(conversationId)));
                    returnText = response.getResult().getOutput().getText();
                }
        
                case "HotelRoom" -> {
                    System.out.println("userQuery classified as type: HotelRoom!");
                    List<HotelRoom> rooms = hotelRoomEmbeddingService.findTop5BySimilarity(userQueryEmbedding);
                    String roomData = rooms.stream().map(HotelRoom::toJsonObjectString)
                            .collect(Collectors.joining("\n"));
                    SystemMessage hotelRoomContextMessage = new SystemMessage(systemMessageHeader + roomData);
                    chatMemory.add(conversationId, hotelRoomContextMessage);
                    chatMemory.add(conversationId, new UserMessage(userQuery));
                    ChatResponse response = chatModel.call(new Prompt(chatMemory.get(conversationId)));
                    returnText = response.getResult().getOutput().getText();
                }
        
                case "Booking" -> {
                    System.out.println("userQuery classified as type: Booking!");
                    List<Message> messages = new ArrayList<>();
                    List<Message> conversationHistory = chatMemory.get(conversationId);
                    long numSystemMessage = conversationHistory.stream()
                            .filter(message -> message instanceof SystemMessage)
                            .count();
                    if (numSystemMessage < 2) {
                        List<Hotel> hotels = hotelEmbeddingService.findTop3BySimilarity(userQueryEmbedding);
                        String hotelData = hotels.stream().map(Hotel::toJsonObjectString)
                                .collect(Collectors.joining("\n"));
                        SystemMessage hotelContextMessage = new SystemMessage(systemMessageHeader + hotelData);
                        chatMemory.add(conversationId, hotelContextMessage);
                        chatMemory.add(conversationId, new UserMessage(userQuery));
                    }
                    String bookingChatbotInitalContext = """
                            Based on conversational context of the following messages identify a single hotel the user wishes to book a stay at. \
                            If unable to confidently determine a single hotel, return the hotelId of the closest match. \
                            The messages can include JSON data as well as user and AI assistant text responses. Do not fabricate data. \
                            Only return a response in this format: \"hotelToBook: data\", where data is the hotelId value of the hotel you've identified. 
                            """;
                    messages.add(new SystemMessage(bookingChatbotInitalContext));
                    messages.addAll(chatMemory.get(conversationId));
                    ChatResponse response = chatModel.call(new Prompt(messages));
                    returnText = response.getResult().getOutput().getText();
                    // chatMemory.add(conversationId, new AssistantMessage(returnText));
                    // return returnText; // early return, if this is triggered than the conversation ends on client-side
                }
        
                default -> {
                    System.out.println("User query is not about Hotel or HotelRoom! Break!");
                    returnText = "Sorry, I couldn't understand your request.";
                }
            }
        
        } catch (IOException err) {
            err.printStackTrace();
            System.out.println("Error thrown in ChatService.generateResponse()!");
            returnText = "Something went wrong. Please try again later.";
        }
        
        chatMemory.add(conversationId, new AssistantMessage(returnText));
        return returnText;
    }

}
