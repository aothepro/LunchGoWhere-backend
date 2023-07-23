package com.gdsswechallenge.lunchlocation.session;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SessionRepository extends MongoRepository<Session, String>  {
    List<Session> findByActiveTrue();

}
