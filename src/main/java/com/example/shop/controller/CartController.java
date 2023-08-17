package com.example.shop.controller;

import com.example.shop.dao.ProductDAO;
import com.example.shop.dao.UserDAO;
import com.example.shop.entity.Product;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class.getName());
    private UserDAO userDAO;
    private ProductDAO productDAO;

    @Autowired
    public CartController(UserDAO userDAO, ProductDAO productDAO){
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getCart(@RequestParam String login){
        logger.info("Getting cart with params: login = " + login);
        return new ResponseEntity<>(userDAO.getUserByLogin(login).get()
                .getCart()
                .stream()
                .map(id -> productDAO.getProduct(id))
                .toList(), HttpStatus.OK);
    }
    @PostMapping("add_to_cart")
    @Transactional
    public ResponseEntity<HttpStatus> addToCart(@RequestParam String login, @RequestParam("product_id") String id){
        logger.info("Adding to cart with params: login = " + login + ", product_id = " + id);
        if(productDAO.exists(Long.valueOf(id))){
            userDAO.addToCart(login, Long.valueOf(id));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("remove_from_cart")
    @Transactional
    public ResponseEntity<HttpStatus> removeFromCart(@RequestParam String login, @RequestParam("product_id") String id){
        if(productDAO.exists(Long.valueOf(id))){
            userDAO.removeFromCart(login, Long.valueOf(id));
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}