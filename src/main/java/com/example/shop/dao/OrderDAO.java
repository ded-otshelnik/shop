package com.example.shop.dao;

import java.util.Optional;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.example.shop.entity.Order;
import com.example.shop.entity.OrderItem;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.repo.OrderRepository;
import com.example.shop.repo.ProductRepository;
import com.example.shop.repo.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderDAO {
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    @Autowired
    public OrderDAO(OrderRepository orderRepository,
                    ProductRepository productRepository,
                    UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<String> createOrder(String login){
        Order order = new Order();
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if(optionalUser.isEmpty()){
            return new ResponseEntity<String>("Invalid user id. Order wasn't created.",HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();
        order.setUser(user);
        if(user.getCart().isEmpty()){
            return new ResponseEntity<String>("Empty cart. Order wasn't created.",HttpStatus.NO_CONTENT);
        }
        List<Product> cart = user.getCart().stream().map(id->productRepository.findById(id.longValue())).toList();
        for(var prod:cart.stream().distinct().toList()){
            OrderItem item = new OrderItem(prod, (long)Collections.frequency(cart, prod));
            order.addProduct(item);
        }
        user.clearCart();
        userRepository.save(user);
        orderRepository.save(order);
        return new ResponseEntity<String>("Order was created",HttpStatus.CREATED);
    }
    @Transactional
    public ResponseEntity<String> deleteOrder(Long orderId){
        if(!orderRepository.existsById(orderId)){
            return new ResponseEntity<String>("Invalid order id. Order wasn't deleted.",HttpStatus.BAD_REQUEST);
        }
        orderRepository.deleteById(orderId);
        return new ResponseEntity<String>("Order was deleted",HttpStatus.OK);
    }

    public Set<Order> getUserOrders(String login){
        Optional<User> user = userRepository.findByLogin(login);
        if(user.isPresent()){
            return user.get().getOrders();
        }
        return null;
    }
}
