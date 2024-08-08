package com.example.shop.dao;

import java.util.Optional;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.example.shop.entity.Order;
import com.example.shop.entity.OrderItem;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.repo.OrderItemRepository;
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
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderDAO(OrderRepository orderRepository,
                    OrderItemRepository orderItemRepository,
                    ProductRepository productRepository,
                    UserRepository userRepository){
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponseEntity<String> createOrder(String login){
        // get user that makes a new order
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()){
            return new ResponseEntity<String>("Invalid user login. Order wasn't created.",HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();

        if (user.getCart().isEmpty()){
            return new ResponseEntity<String>("Empty cart. Order wasn't created.",HttpStatus.NO_CONTENT);
        }
        // get all products that were ordered
        List<Product> cart = user.getCart()
                                 .stream()
                                 .map(id -> productRepository.findById(id.longValue()))
                                 .toList();

        Order order = new Order();

        // make OrderItem objects to avoid repetition
        for(var prod: cart.stream().distinct().toList()){
            OrderItem item = new OrderItem(prod, (long)Collections.frequency(cart, prod));
            orderItemRepository.save(item);
            order.addProduct(item);
        }

        user.clearCart();
        order.setUser(user);

        userRepository.save(user);
        orderRepository.save(order);

        return new ResponseEntity<String>("Order was created", HttpStatus.CREATED);
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

    public ResponseEntity<List<Order>> getUserOrders(String login){
        Optional<User> optionalUser = userRepository.findByLogin(login);
        
        return optionalUser.map(user -> new ResponseEntity<>(orderRepository.findAllByUser(user), HttpStatus.OK))
                           .orElseGet(() -> new ResponseEntity<>(Collections.emptyList(), HttpStatus.BAD_REQUEST));
    }
}
