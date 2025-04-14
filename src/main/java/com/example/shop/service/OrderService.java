package com.example.shop.service;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import com.example.shop.entity.*;
import com.example.shop.exception.CartIsEmptyException;
import com.example.shop.repo.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private final Cart cart;

    @Transactional
    public void createOrder(String login){
        // get user that makes a new order
        Optional<User> optionalUser = userRepository.findByUsername(login);
        if (optionalUser.isEmpty()){
            throw new UsernameNotFoundException("Invalid user login");
        }
        User user = optionalUser.get();

        if (cart.isEmpty()){
            throw new CartIsEmptyException("Cart is empty");
        }



        // copy info from cart and set user who owns the order
        Order order = new Order();
        order.setUser(user);
        order.setOrderItems(cart.getItems());
        order.setPrice(cart.getPrice());
        orderRepository.save(order);

        // delete all cart items after order creation
        cart.clearCart();
    }

    @Transactional
    public void deleteOrder(String login, Long orderId){
        if (userRepository.existsByUsername(login)){
            throw new UsernameNotFoundException("Invalid login.");
        }
        if (!orderRepository.existsById(orderId)){
            throw new RuntimeException("Invalid order id.");
        }
        Order order = orderRepository.getReferenceById(orderId);
        if (order.getUser().equals(login)){
            throw new RuntimeException("Invalid login.");
        }

        orderRepository.deleteById(orderId);
    }

    public List<Order> getUserOrders(String login){
        Optional<User> optionalUser = userRepository.findByUsername(login);
        return optionalUser.map(orderRepository::findAllByUser)
                .orElseThrow(() -> new UsernameNotFoundException("Incorrect login"));
    }
}
