package com.gdsswechallenge.lunchlocation.user;

import java.util.UUID;

public class User {
    private String id;
    private String name;
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
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
