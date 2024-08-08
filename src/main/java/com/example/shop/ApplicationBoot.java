package com.example.shop;

import com.example.shop.entity.Product;
import com.example.shop.entity.Role;
import com.example.shop.entity.User;
import com.example.shop.repo.ProductRepository;
import com.example.shop.repo.UserRepository;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class ApplicationBoot implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationBoot.class.getName());
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String needToUploadDB;

    private final UserRepository userRepo;
    private final ProductRepository prodRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ApplicationBoot(UserRepository userRepo, ProductRepository prodRepo,
                           BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepo = userRepo;
        this.prodRepo = prodRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void onApplicationEvent(final @NonNull ApplicationReadyEvent event) {
        if (needToUploadDB.equals("create-drop")||
                needToUploadDB.equals("drop-and-create")||
                needToUploadDB.equals("create")){
            User user = new User("test", bCryptPasswordEncoder.encode("test"), Role.USER);
            userRepo.save(user);
            User admin = new User("andrey", bCryptPasswordEncoder.encode("admin"), Role.ADMIN);
            userRepo.save(admin);
            Product product = new Product("test",8.9);
            prodRepo.save(product);
       }
    }
}