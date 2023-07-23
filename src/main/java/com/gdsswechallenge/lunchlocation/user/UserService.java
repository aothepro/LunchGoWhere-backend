package com.gdsswechallenge.lunchlocation.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();

    }
    public Optional<User> getUserById(String userId){
        return userRepository.findById(userId);
    }

    public void saveUser(User user) {
        userRepository.save(user);

    }

}
