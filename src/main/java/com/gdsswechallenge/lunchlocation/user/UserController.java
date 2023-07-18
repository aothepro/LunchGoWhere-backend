package com.gdsswechallenge.lunchlocation.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping(path = "/signUp")
    public void signUp(@RequestBody User user) {
        userService.saveUser(user);
    }

}
