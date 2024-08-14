package com.example.shop.service;

import com.example.shop.entity.Role;
import com.example.shop.entity.User;
import com.example.shop.entity.jwt.JwtRequest;
import com.example.shop.repo.RoleRepository;
import com.example.shop.repo.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Transactional(readOnly = true)
    public ResponseEntity<User> checkCredentials(String login, String password){
        Optional<User> optionalUser = userRepository.findByUsername(login);
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
    public void registerNewUser(JwtRequest request){
        if (userRepository.existsByUsername(request.getLogin())){
            throw new BadCredentialsException("User with this credentials is already exist");
        }

        Role role = roleRepository.findByName("USER").orElseThrow();
        User user = new User(request.getLogin(), bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(role));

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException(username);

        User user = optionalUser.get();

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
