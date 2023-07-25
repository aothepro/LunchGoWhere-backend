package com.gdsswechallenge.lunchlocation.vote;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "vote")
public class Vote {
    @Id
    public String id;
    @Indexed
    public String sessionId;
    @Indexed
    public String voterId;
    public String restaurantName;
}
