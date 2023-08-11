package com.example.shop;

import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.repo.OrderRepository;
import com.example.shop.repo.ProductRepository;
import com.example.shop.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationBoot implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationBoot.class.getName());
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String needToUploadDB;
    @Autowired
    UserRepository userRepo;
    @Autowired
    ProductRepository prodRepo;
    @Autowired
    OrderRepository orderRepo;
    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        if (needToUploadDB.equals("create-drop")||
                needToUploadDB.equals("drop-and-create")||
                needToUploadDB.equals("create")){
            User user = new User("test", "test", "test", null, null, null, null);
            userRepo.save(user);
            Product product = new Product("test",8.9);
            prodRepo.save(product);
        }
    }
}