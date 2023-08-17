package com.example.shop.dao;

import com.example.shop.entity.User;
import com.example.shop.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDAO {
    private UserRepository userRepository;
    @Autowired
    public UserDAO(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> checkCredentials(String login, String password){
        var users=userRepository.findAll();
        for(var user:users){
            if(user.getLogin().equals(login)) {
                if(user.getPassword().equals(password)){
                    return new ResponseEntity<String>("Authorization is successful.", HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Incorrect password.", HttpStatus.FORBIDDEN);
                }
            }
        }
        return new ResponseEntity<String>("Incorrect login.", HttpStatus.FORBIDDEN);
    }
    @Transactional
    public ResponseEntity<String> registerNewUser(String login, String password, String surname,
                                                  String phoneNumber, String email){
        if(userRepository.existsByLogin(login)){
            return new ResponseEntity<String>("Invalid login (already exists). Please choose another login.", HttpStatus.FOUND)  ;
        }
        User newUser = new User(surname, login, password, email, phoneNumber, "default",null);
        userRepository.save(newUser);
        return new ResponseEntity<String>("User account was created.", HttpStatus.OK);
    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    public Optional<User> getUserByLogin(String login){
        return userRepository.findByLogin(login);
    }

    @Transactional
    public void addToCart(String login, long id){
        User user = userRepository.findByLogin(login).get();
        user.addToCart(id);
        userRepository.save(user);
    }
    public void removeFromCart(String login, long id){
        User user = userRepository.findByLogin(login).get();
        user.removeFromCart(id);
        userRepository.save(user);
    }
    public void addToFav(String login, long id){
        User user = userRepository.findByLogin(login).get();
        user.addToFav(id);
        userRepository.save(user);
    }
    public void removeFromFav(String login, long id){
        User user = userRepository.findByLogin(login).get();
        user.removeFromFav(id);
        userRepository.save(user);
    }
}
