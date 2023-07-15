package com.gdsswechallenge.lunchlocation.user;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    public List<User> getUsers() {
              return List.of(new User("alpha", "alpha3423"),
                new User("beta", "beta54216"));

    }
}
