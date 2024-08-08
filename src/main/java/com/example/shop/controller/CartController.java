package com.example.shop.controller;

import com.example.shop.dao.ProductDAO;
import com.example.shop.dao.UserDAO;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class.getName());

    private final UserDAO userDAO;
    private final ProductDAO productDAO;

    @Autowired
    public CartController(UserDAO userDAO, ProductDAO productDAO){
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    @GetMapping
    public ResponseEntity<?> getCart(@RequestParam String login){
        logger.info("Getting cart with params: login = {}", login);

        Optional<User> optionalUser = userDAO.getUserByLogin(login);

        return optionalUser.map(user -> ResponseEntity.ok(user
                .getCart()
                .stream()
                .map(productDAO::getProduct)
                .toList()))
                .orElseGet(() -> new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND));

    }
    @PostMapping("add_to_cart")
    @Transactional
    public ResponseEntity<HttpStatus> addToCart(@RequestParam String login, @RequestParam("product_id") long id){
        logger.info("Adding to cart with params: login = {}, product_id = {}", login, id);

        if (productDAO.exists(id)){
            userDAO.addToCart(login, id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("remove_from_cart")
    @Transactional
    public ResponseEntity<HttpStatus> removeFromCart(@RequestParam String login, @RequestParam("product_id") long id){
        logger.info("Removing from cart with params: login = {}, product_id = {}", login, id);

        if (productDAO.exists(id)){
            userDAO.removeFromCart(login, id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}