package com.nehemiah.resolveflowai.repository;

import com.nehemiah.resolveflowai.model.AiConversation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiConversationRepository
        extends MongoRepository<AiConversation, String> {
}