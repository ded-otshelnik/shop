package com.example.shop.dao;

import com.example.shop.entity.Role;
import com.example.shop.entity.User;
import com.example.shop.repo.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDAO implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserDAO(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<User> checkCredentials(String login, String password){
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()){
            return new ResponseEntity<>(new User(), HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();
        String expectedPassword = user.getPassword();
        if (!bCryptPasswordEncoder.matches(password, expectedPassword)){
            return new ResponseEntity<>(new User(), HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(user);
    }

    @Transactional
    public ResponseEntity<String> registerNewUser(String login, String password){
        if (userRepository.existsByLogin(login)){
            return new ResponseEntity<>("Invalid login (already exists). Please choose another login.",
                                              HttpStatus.NOT_FOUND);
        }
        User user = new User(login, bCryptPasswordEncoder.encode(password), Role.ROLE_USER);

        userRepository.save(user);
        return ResponseEntity.ok("User account was created.");
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByLogin(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException(username);

        User user = optionalUser.get();

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserByLogin(String login){
        return userRepository.findByLogin(login);
    }

    @Transactional
    public boolean addToCart(String login, long id){
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.addToCart(id);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean removeFromCart(String login, long id){
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.removeFromCart(id);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addToFav(String login, long id){
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.addToFav(id);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean removeFromFav(String login, long id){
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.removeFromFav(id);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
