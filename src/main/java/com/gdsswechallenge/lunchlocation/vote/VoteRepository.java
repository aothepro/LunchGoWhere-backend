package com.gdsswechallenge.lunchlocation.vote;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends MongoRepository<Vote, String> {
    List<Vote> findBySessionId(String sessionId);
    Optional<Vote> findBySessionIdAndVoterId(String sessionId, String voterId);
}
