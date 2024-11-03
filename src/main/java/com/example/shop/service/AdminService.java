package com.example.shop.service;

import com.example.shop.entity.Role;
import com.example.shop.entity.User;
import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.repo.RoleRepository;
import com.example.shop.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public void grantAdminAuthority(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such user"));
        Role admin = roleRepository.findByName("ADMIN").orElseThrow(() -> new ResourceNotFoundException("No such role"));

        user.addRole(admin);

        userRepository.save(user);
    }
}
