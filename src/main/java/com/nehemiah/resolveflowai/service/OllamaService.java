package com.nehemiah.resolveflowai.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OllamaService {

    private final RestClient restClient;
    private final String model;

    public OllamaService(
            @Value("${ollama.base-url}") String baseUrl,
            @Value("${ollama.model}") String model) {

        this.restClient = RestClient.create(baseUrl);
        this.model = model;
    }

    public String generateReply(String prompt) {

        if (prompt == null || prompt.isBlank()) {
            throw new IllegalArgumentException("Prompt must not be empty");
        }

        String systemPrompt = """
                You are ResolveFlow AI, an intelligent issue-resolution assistant.

                Your job is to:
                - Understand the user's problem clearly.
                - Give practical and structured solutions.
                - Provide step-by-step guidance when needed.
                - Ask for missing information only when necessary.
                - Keep answers clear, concise, and useful.
                - Never introduce yourself as Qwen, Ollama, Alibaba, or any underlying AI model.
                - If the user asks who you are, identify yourself only as ResolveFlow AI.

                ResolveFlow AI is a Java Spring Boot based AI application
                designed to analyze user problems and provide structured,
                practical solutions.
                """;

        String finalPrompt = systemPrompt
                + "\n\nUser: "
                + prompt
                + "\n\nResolveFlow AI:";

        Map<String, Object> request = Map.of(
                "model", model,
                "prompt", finalPrompt,
                "stream", false,
                "options", Map.of(
                        "num_ctx", 512,
                        "num_predict", 100
                )
        );

        Map<String, Object> result = restClient.post()
                .uri("/api/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Object>>() {
                });

        if (result == null) {
            return "Unable to generate AI response.";
        }

        Object response = result.get("response");

        if (response == null) {
            return "Unable to generate AI response.";
        }

        return response.toString().trim();
    }
}