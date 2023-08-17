package com.example.shop.controller;

import com.example.shop.dao.ProductDAO;
import com.example.shop.dao.UserDAO;
import com.example.shop.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    private UserDAO userDAO;
    private ProductDAO productDAO;

    @Autowired
    public FavoritesController(UserDAO userDAO,ProductDAO productDAO){
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getFavorites(@RequestParam String login){
        return new ResponseEntity<>(userDAO.getUserByLogin(login).get().getFavorites()
                .stream()
                .map(id->productDAO.getProduct(id))
                .toList(), HttpStatus.OK);
    }
    @PostMapping("add_to_fav")
    public ResponseEntity<HttpStatus> addToFav(@RequestParam String login, @RequestParam("product_id") long id){
        if(productDAO.exists(id)){
            userDAO.addToFav(login,id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("remove_from_fav")
    public ResponseEntity<HttpStatus> removeFromFav(@RequestParam String login, @RequestParam("product_id") long id){
        if(productDAO.exists(id)){
            userDAO.removeFromFav(login,id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}