package com.gdsswechallenge.lunchlocation;

import com.gdsswechallenge.lunchlocation.session.SessionRepository;
import com.gdsswechallenge.lunchlocation.user.User;
import com.gdsswechallenge.lunchlocation.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LunchlocationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LunchlocationApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserRepository repository, PasswordEncoder passwordEncoder, SessionRepository sessionRepository) {
        repository.deleteAll();
//        sessionRepository.deleteAll();
        // Initialize tester users
        return args -> {
            User user =  User.builder()
                    .name("testUserName")
                    .username("tester")
                    .password(passwordEncoder.encode("tester"))
                    .build();


            repository.findByUsername(user.getUsername()).ifPresentOrElse(u -> {
                System.out.println(u + " already exist");
            }, () -> {
                repository.insert(user);
            });
        };
    }

}
