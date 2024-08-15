package com.example.shop.controller;

import java.util.List;
import java.util.Optional;

import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.repo.RoleRepository;
import com.example.shop.service.ProductService;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/admin")
@AllArgsConstructor
@Tag(name = "AdminController", description = "Admin controller")
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final RoleRepository roleRepository;

    @GetMapping("/product/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get product",
            description = "Get product by id"
    )
    @ApiResponse(responseCode = "403")
    public Product getProduct(@PathVariable("product_id") Long id){
        Optional<Product> product = productService.getProduct(id);
        return product.orElseThrow(() -> new ResourceNotFoundException("Incorrect product id"));
    }

    @GetMapping("get-products")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get products",
            description = "Get all products"
    )
    private List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("get-users")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get users",
            description = "Get all users"
    )
    private List<User> getUsers(){
        return userService.getUsers();
    }

}
