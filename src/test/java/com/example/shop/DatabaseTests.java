/*
package com.example.shop;

import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.repo.OrderRepository;
import com.example.shop.repo.ProductRepository;
import com.example.shop.repo.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopApplication.class)
public class DatabaseTests {


    private static final Logger logger = LoggerFactory.getLogger(ApplicationBoot.class.getName());
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String needToUploadDB;

    private final UserRepository userRepo;
    private final ProductRepository prodRepo;
    private final OrderRepository orderRepo;

    @Autowired
    public DatabaseTests(UserRepository userRepo, ProductRepository prodRepo, OrderRepository orderRepo){
        this.userRepo = userRepo;
        this.prodRepo = prodRepo;
        this.orderRepo = orderRepo;
    }

    private void initDatabaseWithTestValues() {
        if (needToUploadDB.equals("create-drop")||
                needToUploadDB.equals("drop-and-create")||
                needToUploadDB.equals("create")){
            User user = new User("test", "test", "test", null, "+1-111-111-11-11", null, null);
            userRepo.save(user);
            Product product = new Product("Product test",8.9);
            prodRepo.save(product);
        }
    }
    @Test
    public void isDatabaseCorrectlyInitializedTest(){
        initDatabaseWithTestValues();
        final String name = "test";
        final String login = "test";

        final User user = userRepo.getByLogin()

    }
}*/
