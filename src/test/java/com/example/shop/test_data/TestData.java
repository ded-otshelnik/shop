//package com.example.shop.test_data;
//
//import com.example.shop.entity.Product;
//import com.example.shop.entity.Role;
//import com.example.shop.entity.User;
//import com.example.shop.exception.ResourceNotFoundException;
//import com.example.shop.repo.RoleRepository;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@Slf4j
//public class TestData {
//    private static final List<User> users = new ArrayList<>();
//
//    private final List<Product> prods = new ArrayList<>();
//
//    private final List<com.example.shop.entity.Order> orders = new ArrayList<>();
//
//    public TestData() {
//        log.info("Add test instances");
//
//        Role admin_role = roleRepository.findByName("ADMIN").orElseThrow(() -> new ResourceNotFoundException("No such role"));
//        Role user_role = roleRepository.findByName("USER").orElseThrow(() -> new ResourceNotFoundException("No such role"));
//
//        User admin = new User("test_admin", "test_admin_pass");
//        admin.addRole(admin_role);
//        User user = new User("test_user", "test_user_pass");
//        user.addRole(user_role);
//
//        users.addAll(List.of(admin, user));
//    }
//}
