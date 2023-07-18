package com.gdsswechallenge.lunchlocation;

import com.gdsswechallenge.lunchlocation.user.User;
import com.gdsswechallenge.lunchlocation.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class LunchlocationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LunchlocationApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserRepository repository, MongoTemplate mongoTemplate) {
        // Initialize some users
        return args -> {
            User user = new User("John doe", "johnydo88");


            repository.findUserByUsername(user.getUsername()).ifPresentOrElse(u -> {
                System.out.println(u + " already exist");
            }, () -> {
                repository.insert(user);
            });
        };
    }

}
