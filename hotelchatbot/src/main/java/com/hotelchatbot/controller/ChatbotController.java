package com.hotelchatbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/chat/{conversationId}")
    public ResponseEntity<String> postMethodName(@PathVariable String conversationId, @RequestBody String userQuery) {
        System.out.println(userQuery + "\n");
        String response = chatService.generateResponse("asdf", userQuery);
        return ResponseEntity.ok().body(response);
    }
    

}
