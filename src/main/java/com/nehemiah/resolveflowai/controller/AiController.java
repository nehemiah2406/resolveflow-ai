package com.nehemiah.resolveflowai.controller;

import java.util.List;

import com.nehemiah.resolveflowai.model.AiConversation;
import com.nehemiah.resolveflowai.service.AiConversationService;
import com.nehemiah.resolveflowai.service.OllamaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final OllamaService ollamaService;
    private final AiConversationService conversationService;

    public AiController(
            OllamaService ollamaService,
            AiConversationService conversationService) {

        this.ollamaService = ollamaService;
        this.conversationService = conversationService;
    }

    @PostMapping("/generate")
    public AiResponse generate(@Valid @RequestBody AiRequest request) {

        String reply = ollamaService.generateReply(request.prompt());

        conversationService.saveConversation(
                request.prompt(),
                reply
        );

        return new AiResponse(reply);
    }

    @GetMapping("/history")
    public List<AiConversation> getHistory() {
        return conversationService.getAllConversations();
    }

    @DeleteMapping("/history")
    public ClearHistoryResponse clearHistory() {

        conversationService.clearHistory();

        return new ClearHistoryResponse(
                "Conversation history cleared successfully."
        );
    }

    public record AiRequest(
            @NotBlank(message = "Prompt must not be empty")
            @Size(max = 2000, message = "Prompt must not exceed 2000 characters")
            String prompt) {
    }

    public record AiResponse(String reply) {
    }

    public record ClearHistoryResponse(String message) {
    }
}