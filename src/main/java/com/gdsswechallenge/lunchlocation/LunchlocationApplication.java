package com.gdsswechallenge.lunchlocation;

import com.gdsswechallenge.lunchlocation.session.Session;
import com.gdsswechallenge.lunchlocation.session.SessionRepository;
import com.gdsswechallenge.lunchlocation.user.User;
import com.gdsswechallenge.lunchlocation.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class LunchlocationApplication {

    public static void main(String[] args) {
        SpringApplication.run(LunchlocationApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(UserRepository userRepository, PasswordEncoder passwordEncoder, SessionRepository sessionRepository) {
        userRepository.deleteAll();
        sessionRepository.deleteAll();
        // Initialize tester users and seed data
        return args -> {
            // Create a test user
            User testUser = User.builder().name("testUserName").username("tester").password(passwordEncoder.encode("tester")).build();
            userRepository.findByUsername(testUser.getUsername()).ifPresentOrElse(u -> {
                System.out.println(u + " already exist");
            }, () -> {
                userRepository.insert(testUser);
            });

            // Create a user and use it to create 3 sessions
            User sessionCreatorUser = User.builder().name("SessionCreator757").username("sessioncreator757").password(passwordEncoder.encode("123456")).build();
            userRepository.findByUsername(sessionCreatorUser.getUsername()).ifPresentOrElse(u -> {
                System.out.println(u + " already exist");
            }, () -> {
                User user = userRepository.save(sessionCreatorUser);
                String sessionCreatorUserId = user.getId();
                sessionRepository.save(Session.builder().name("YesterdayLunchSession").isActive(true).creatorId(sessionCreatorUserId).lunchDate(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).minusDays(1).plusHours(8)).build());
                sessionRepository.save(Session.builder().name("todayLunchSession").isActive(true).creatorId(sessionCreatorUserId).lunchDate(LocalDateTime.now().minusDays(0).withHour(0).withMinute(0).withSecond(0).plusHours(8)).build());
                sessionRepository.save(Session.builder().name("tomorrowLunchSession").isActive(true).creatorId(sessionCreatorUserId).lunchDate(LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).plusHours(8)).build());
            });


        };
    }

}
