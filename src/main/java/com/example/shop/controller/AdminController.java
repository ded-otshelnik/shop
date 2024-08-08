package com.example.shop.controller;

import java.util.List;

import com.example.shop.dao.ProductDAO;
import com.example.shop.dao.UserDAO;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

    private final UserDAO userDAO;
    private final ProductDAO productDAO;

    @Autowired
    public AdminController(UserDAO userDAO, ProductDAO productDAO){
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
        return new ResponseEntity<>(productDAO.getProducts(), HttpStatus.OK);
    }
    @GetMapping("get-users")
    private ResponseEntity<List<User>> GetUsers(){
        return new ResponseEntity<>(userDAO.getUsers(), HttpStatus.OK);
    }

}
