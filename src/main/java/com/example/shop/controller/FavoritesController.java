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
    @Autowired
    private UserDAO usersDAO;
    @Autowired
    private ProductDAO productDAO;

    @GetMapping
    public ResponseEntity<List<Product>> getFavorites(@RequestParam String login){
        return new ResponseEntity<>(usersDAO.getUserByLogin(login).get().getFavorites()
                .stream()
                .map(id->productDAO.getProduct(id))
                .toList(), HttpStatus.OK);
    }
    @PostMapping("add_to_fav")
    public ResponseEntity<HttpStatus> addToFav(@RequestParam String login, @RequestParam("product_id") long id){
        if(productDAO.exists(id)){
            usersDAO.addToFav(login,id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("remove_from_fav")
    public ResponseEntity<HttpStatus> removeFromFav(@RequestParam String login, @RequestParam("product_id") long id){
        if(productDAO.exists(id)){
            usersDAO.removeFromFav(login,id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}