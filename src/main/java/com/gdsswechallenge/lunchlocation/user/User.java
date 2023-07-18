package com.gdsswechallenge.lunchlocation.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Builder
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String username;


    public User(String name, String username) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", username='" + username + '\'' + '}';
    }
}
