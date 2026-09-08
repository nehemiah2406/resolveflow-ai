package com.nehemiah.resolveflowai.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.nehemiah.resolveflowai.model.Issue;

public interface IssueRepository extends MongoRepository<Issue, String> {
}
