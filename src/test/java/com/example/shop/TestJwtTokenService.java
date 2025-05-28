package com.example.shop;

import com.example.shop.entity.Role;
import com.example.shop.entity.User;
import com.example.shop.service.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("integration-test")
@SpringBootTest
@Slf4j
public class TestJwtTokenService {

    @Autowired
    public JwtTokenService jwtTokenService;

    @Test
    public void testJwtTokenServiceOnSimpleUser(){
        Role role = new Role("USER");
        User simpleUser = new User("test_simple_user","test_pwd");
        simpleUser.addRole(role);

        String token = jwtTokenService.generateToken(simpleUser);

        Assert.assertTrue("JWT generated token that does not match to user",
                jwtTokenService.validateToken(token, simpleUser));
    }

}
