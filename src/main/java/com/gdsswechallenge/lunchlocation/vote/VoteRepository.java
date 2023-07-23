package com.gdsswechallenge.lunchlocation.vote;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VoteRepository extends MongoRepository<Vote, String> {
    List<Vote> findBySessionId(String sessionId);
}
