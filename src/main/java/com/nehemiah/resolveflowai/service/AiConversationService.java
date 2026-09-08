package com.nehemiah.resolveflowai.service;

import java.util.List;

import com.nehemiah.resolveflowai.model.AiConversation;
import com.nehemiah.resolveflowai.repository.AiConversationRepository;

import org.springframework.stereotype.Service;

@Service
public class AiConversationService {

    private final AiConversationRepository repository;

    public AiConversationService(AiConversationRepository repository) {
        this.repository = repository;
    }

    public AiConversation saveConversation(String prompt, String reply) {
        AiConversation conversation = new AiConversation(prompt, reply);
        return repository.save(conversation);
    }

    public List<AiConversation> getAllConversations() {
        return repository.findAll();
    }

    public void clearHistory() {
        repository.deleteAll();
    }
}