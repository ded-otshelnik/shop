package com.example.shop.controller;

import com.example.shop.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDAO usersDAO;

    @GetMapping("/login")
    public ResponseEntity<String> Authorization(@RequestParam("login") String login, @RequestParam("password") String password){
        return usersDAO.checkCredentials(login, password);
    }
    @GetMapping("/register")
    public ResponseEntity<String> Register(@RequestParam("surname") String surname,
                                           @RequestParam("login") String login,
                                           @RequestParam("password") String password,
                                           @RequestParam("phone_number") String phoneNumber,
                                           @RequestParam("email") String email){
        return usersDAO.registerNewUser(login, password, surname, phoneNumber, email);
    }
}
