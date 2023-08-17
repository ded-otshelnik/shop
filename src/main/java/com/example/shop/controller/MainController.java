package com.example.shop.controller;

import java.util.List;
import java.util.Set;

import com.example.shop.dao.ProductDAO;
import com.example.shop.dao.UserDAO;
import com.example.shop.entity.Product;
import com.example.shop.entity.Review;
import com.example.shop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private UserDAO userDAO;
    private ProductDAO productDAO;

    @Autowired
    public MainController(UserDAO userDAO,ProductDAO productDAO){
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    @GetMapping("{goodId}")
    public ResponseEntity<Product> getProduct(@PathVariable("goodId") long id){
        Product product = productDAO.getProduct(id);
        if(product != null){
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("get-products")
    private ResponseEntity<List<Product>> GetProducts(){
        return new ResponseEntity<List<Product>>(productDAO.getProducts(), HttpStatus.OK);
    }
    @GetMapping("get-users")
    private ResponseEntity<List<User>> GetUsers(){
        return new ResponseEntity<List<User>>(userDAO.getUsers(), HttpStatus.OK);
    }
    @GetMapping("get-reviews")
    private ResponseEntity<Set<Review>> GetReviews(){
        return new ResponseEntity<Set<Review>>(userDAO.getUsers().get(0).getReviews(), HttpStatus.OK);
    }

}
