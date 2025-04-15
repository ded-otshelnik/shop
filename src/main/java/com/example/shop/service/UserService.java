package com.example.shop.service;

import com.example.shop.entity.Role;
import com.example.shop.entity.User;
import com.example.shop.entity.jwt.JwtRequest;
import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.repo.RoleRepository;
import com.example.shop.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void registerNewUser(JwtRequest request){
        if (userRepository.existsByUsername(request.getLogin())){
            throw new BadCredentialsException("User with this credentials is already exist");
        }

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new ResourceNotFoundException("No such role"));
        User user = new User(request.getLogin(), bCryptPasswordEncoder.encode(request.getPassword()));
        user.addRole(role);

        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException(username);

        User user = optionalUser.get();

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
