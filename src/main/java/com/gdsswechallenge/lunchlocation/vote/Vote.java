package com.gdsswechallenge.lunchlocation.vote;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class Vote {
    @Id
    public String id;
    @Indexed
    public String sessionId;
    public String voterId;
    public String restaurantName;
}
