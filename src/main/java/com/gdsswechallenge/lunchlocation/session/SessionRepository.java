package com.gdsswechallenge.lunchlocation.session;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session, String>  {

}
