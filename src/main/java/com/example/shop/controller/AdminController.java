package com.example.shop.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.example.shop.entity.Role;
import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.repo.RoleRepository;
import com.example.shop.service.ProductService;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.service.UserService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/admin")
@AllArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final RoleRepository roleRepository;

    @GetMapping("/product/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProduct(@PathVariable("product_id") Long id, Principal principal){
        Optional<Product> product = productService.getProduct(id);
        return product.orElseThrow(() -> new ResourceNotFoundException("Incorrect product id"));
    }

    @GetMapping("get-products")
    @ResponseStatus(HttpStatus.OK)
    private List<Product> getProducts(Principal principal){
        return productService.getProducts();
    }
    @GetMapping("get-users")
    @ResponseStatus(HttpStatus.OK)
    private List<User> getUsers(Principal principal){
        return userService.getUsers();
    }

    @GetMapping("get-roles")
    @ResponseStatus(HttpStatus.OK)
    private List<Role> getRoles(Principal principal){
        return roleRepository.findAll();
    }

}
