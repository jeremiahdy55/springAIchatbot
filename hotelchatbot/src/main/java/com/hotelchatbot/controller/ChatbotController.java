package com.hotelchatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelchatbot.openai.ChatService;


@RestController
@RequestMapping("/chatbot")
public class ChatbotController {

    @Autowired
    ChatService chatService;

    @PostMapping("/chat")
    public String postMethodName(@RequestBody String userQuery) {
        System.out.println(userQuery + "\n");
        return chatService.generateResponse("asdf", userQuery);
    }
    

}
