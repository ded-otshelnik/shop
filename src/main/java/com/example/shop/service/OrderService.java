package com.example.shop.service;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import com.example.shop.entity.*;
import com.example.shop.exception.CartIsEmptyException;
import com.example.shop.repo.OrderItemRepository;
import com.example.shop.repo.OrderRepository;
import com.example.shop.repo.ProductRepository;
import com.example.shop.repo.UserRepository;

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
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;


    @Transactional
    public void createOrder(String login, Cart cart){
        // get user that makes a new order
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()){
            throw new UsernameNotFoundException("Invalid user login");
        }
        User user = optionalUser.get();

        if (cart.isEmpty()){
            throw new CartIsEmptyException("Cart is empty");
        }

        Order order = new Order();
        orderRepository.save(order);

        for (var item: cart.getItems()){
            item.setOrder(order);
            orderItemRepository.save(item);
        }

        cart.clearCart();
        order.setUser(user);
    }

    @Transactional
    public ResponseEntity<String> deleteOrder(String login, Long orderId){
        if (userRepository.existsByLogin(login)){
            return new ResponseEntity<String>("Invalid login.", HttpStatus.BAD_REQUEST);
        }
        if (!orderRepository.existsById(orderId)){
            return new ResponseEntity<String>("Invalid order id.", HttpStatus.BAD_REQUEST);
        }
        Order order = orderRepository.getReferenceById(orderId);
        if (order.getUser().equals(login)){
            return new ResponseEntity<String>("Invalid login.", HttpStatus.BAD_REQUEST);
        }

        orderRepository.deleteById(orderId);

        return new ResponseEntity<String>("Order was deleted", HttpStatus.OK);
    }

    public List<Order> getUserOrders(String login){
        Optional<User> optionalUser = userRepository.findByLogin(login);
        return optionalUser.map(orderRepository::findAllByUser).orElseThrow(() -> new UsernameNotFoundException("Incorrect login"));
    }
}
