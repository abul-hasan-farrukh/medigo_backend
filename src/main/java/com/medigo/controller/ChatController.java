package com.medigo.controller;

import com.medigo.dto.ChatRequestDTO;
import com.medigo.dto.ChatResponseDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    @Value("${cohere.api.key}")
    private String apiKey;

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.cohere.ai/v1")
            .build();

    @PostMapping
    public ResponseEntity<?> chat(@RequestBody ChatRequestDTO request) {

        try {

            String prompt = """
                    You are a pathology expert chatbot.

                    Strict Rules:
                    - Answer ONLY pathology-related questions
                    - If not related, reply EXACTLY: "It's not my context"
                    - Return ONLY clean HTML

                    User Question:
                    "%s"
                    """.formatted(request.getQuestion());

            Map<String, Object> body = new HashMap<>();
            body.put("model", "command-a-03-2025");
            body.put("message", prompt);

            Map response = webClient.post()
                    .uri("/chat")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            String aiText = "No response";

            if (response != null && response.get("text") != null) {
                aiText = response.get("text").toString();
            }

            aiText = cleanHTML(aiText);

            return ResponseEntity.ok(new ChatResponseDTO(aiText));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ChatResponseDTO("Error: " + e.getMessage()));
        }
    }

    private String cleanHTML(String text) {

        return text
                .replace("```html", "")
                .replace("```", "")
                .replaceAll("(?i)\\*\\*Explanation:[\\s\\S]*", "")
                .trim();
    }
}