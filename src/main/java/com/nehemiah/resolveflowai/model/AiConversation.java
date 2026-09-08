package com.nehemiah.resolveflowai.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ai_conversations")
public class AiConversation {

    @Id
    private String id;

    private String prompt;
    private String reply;
    private LocalDateTime createdAt;

    public AiConversation() {
    }

    public AiConversation(String prompt, String reply) {
        this.prompt = prompt;
        this.reply = reply;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getReply() {
        return reply;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}