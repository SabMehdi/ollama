package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




@RestController
public class ChatController {

       private final OllamaChatClient chatClient;
    private List<String> conversationHistory = new ArrayList<>();

    @Autowired
    public ChatController(OllamaChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        conversationHistory.add(message);
        String conversation = String.join(" ", conversationHistory);
        String response = chatClient.call(conversation);
        conversationHistory.add(response);
        System.out.println( "conversationHistory: " + conversationHistory);
        return Map.of("generation", response);
    }
}