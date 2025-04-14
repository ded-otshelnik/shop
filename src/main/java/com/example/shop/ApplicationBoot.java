package com.example.shop;

import com.example.shop.entity.Product;
import com.example.shop.entity.Role;
import com.example.shop.entity.User;
import com.example.shop.repo.ProductRepository;
import com.example.shop.repo.RoleRepository;
import com.example.shop.repo.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationBoot implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String needToUploadDB;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void onApplicationEvent(final @NonNull ApplicationReadyEvent event) {
        if (needToUploadDB.equals("create-drop") ||
            needToUploadDB.equals("drop-and-create") ||
            needToUploadDB.equals("create")){

            Role user_role = new Role("USER");
            roleRepository.save(user_role);

            Role admin_role = new Role("ADMIN");
            roleRepository.save(admin_role);

            User admin = new User("andrey", bCryptPasswordEncoder.encode("admin"));
            admin.addRole(admin_role);
            admin.addRole(user_role);
            userRepository.save(admin);

            User user = new User("test", bCryptPasswordEncoder.encode("test"));
            user.addRole(user_role);
            userRepository.save(user);

            Product product = new Product("bread",8.9);
            productRepository.save(product);

            product = new Product("milk",9.1);
            productRepository.save(product);
       }
    }
}