package com.gdsswechallenge.lunchlocation.session;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "user")
public class Session {
    private String name;

}
