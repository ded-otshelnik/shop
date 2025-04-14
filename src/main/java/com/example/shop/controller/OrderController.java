package com.example.shop.controller;

import com.example.shop.service.OrderService;
import com.example.shop.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/orders")
@RequiredArgsConstructor
@Tag(name = "OrderController", description = "Order controller")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("create-order")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Create order",
            description = "Create order linked to user"
    )
    public void createOrder(@RequestParam String username){
        orderService.createOrder(username);
    }

    @DeleteMapping("delete-order")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Delete order",
            description = "Delete order by order id"
    )
    public void deleteOrder(@RequestParam String username, @RequestParam("order_id") Long orderId){
        orderService.deleteOrder(username, orderId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get orders",
            description = "Get all orders for user"
    )
    public List<Order> getOrders(@RequestParam String username){
        return orderService.getUserOrders(username);
    }
}