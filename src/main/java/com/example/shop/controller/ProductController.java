package com.example.shop.controller;

import com.example.shop.entity.Product;
import com.example.shop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PutMapping("/update/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update product price",
            description = "Set product price to new value"
    )
    public void updatePrice(@PathVariable("product_id") Long productId, Double newPrice){
        productService.updatePrice(productId, newPrice);
    }

    @GetMapping("/{product_id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get product",
            description = "Get product by id"
    )
    @ApiResponse(responseCode = "403")
    @ApiResponse(responseCode = "200")
    public Product getProduct(@PathVariable("product_id") Long id){
        return productService.getProduct(id);
    }

    @GetMapping("/get-products")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get products",
            description = "Get all products"
    )
    @ApiResponse(responseCode = "200")
    private List<Product> getProducts(){
        return productService.getProducts();
    }
}
