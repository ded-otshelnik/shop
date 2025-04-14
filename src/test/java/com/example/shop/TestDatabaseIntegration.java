package com.example.shop;

import com.example.shop.entity.Product;
import com.example.shop.entity.Role;
import com.example.shop.entity.User;
import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.repo.OrderRepository;
import com.example.shop.repo.ProductRepository;
import com.example.shop.repo.RoleRepository;
import com.example.shop.repo.UserRepository;
import com.example.shop.service.AdminService;
import com.example.shop.service.JwtTokenService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.*;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@ActiveProfiles("integration-test")
@SpringBootTest(classes = ShopApplication.class,
                webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
public class TestDatabaseIntegration {

    @LocalServerPort
    public int port;

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:11.1")
            .withDatabaseName("integration_tests_db");


    // test lists that will be used as "expected"
    private final List<User> users = new ArrayList<>();
    private final List<Product> prods = new ArrayList<>();
    private final List<com.example.shop.entity.Order> orders = new ArrayList<>();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ProductRepository prodRepository;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AdminService adminService;
    @Autowired
    JwtTokenService jwtTokenService;

    @BeforeAll
    public void setUp() {
        log.info("Add test instances");
        Role admin_role = roleRepository.findByName("ADMIN").orElseThrow(() -> new ResourceNotFoundException("No such role"));
        Role user_role = roleRepository.findByName("USER").orElseThrow(() -> new ResourceNotFoundException("No such role"));

        User admin = new User("test_admin", "test_admin_pass");
        admin.addRole(admin_role);
        User user = new User("test_user", "test_user_pass");
        user.addRole(user_role);

        users.addAll(List.of(admin, user));
    }

    static {
        RestAssured.urlEncodingEnabled = false;
        postgres.start();
    }

    @Test
    @Order(1)
    public void testA_ConnectionToDatabase(){
        log.info("First test");
        Assertions.assertNotNull(userRepository);
        Assertions.assertNotNull(roleRepository);
        Assertions.assertNotNull(prodRepository);
        Assertions.assertNotNull(orderRepository);
    }



    @Test
    @Order(2)
    @Rollback(false)
    public void testB_AddUsers() throws Exception{
        log.info("Second test");
        setUp();
        RestAssured.baseURI = "http://localhost:" + port + "/auth/register";

        for(User user: users){
            // arrange
            JSONObject json_user = new JSONObject();
            json_user.put("login", user.getUsername());
            json_user.put("password", user.getPassword());
            log.info("Add user {}", user.getUsername());

            // act
            Response response = given()
                                    .accept(ContentType.JSON)
                                    .contentType(ContentType.JSON)
                                    .body(json_user.toString())
                                .when()
                                    .post();

            var body = response.getBody();

            // assert
            Assertions.assertEquals(HttpStatus.OK,
                                    HttpStatus.valueOf(response.statusCode()));
            Assertions.assertTrue(userRepository.existsByUsername(user.getUsername()));

            String token = body.jsonPath().getString("token");
            Assertions.assertTrue(jwtTokenService.validateToken(token, user));
        }
    }

    @Test
    @Order(3)
    @Rollback(false)
    public void testC_PermissionsForUsersAndAdmins() throws Exception{
        log.info("Third test");
        RestAssured.baseURI = "http://localhost:" + port + "/api/admin/get-users";

        // arrange
        User user = userRepository.findByUsername("test_user").orElseThrow(() -> new RuntimeException("No such user"));
        String token = jwtTokenService.generateToken(user);
        given()
                .header("Authorization", "Bearer " + token)
        // act
        .when()
                .get()
        // assert
        .then()
                .statusCode(HttpStatus.FORBIDDEN.value());

        // make test_admin be real admin in test environment
        adminService.grantAdminAuthority("test_admin");
        user = userRepository.findByUsername("test_admin").orElseThrow(() -> new RuntimeException("No such user"));
        token = jwtTokenService.generateToken(user);

        // arrange
        given()
             .header("Authorization", "Bearer " + token)
        // act
        .when()
             .get()
        // assert
        .then()
             .statusCode(HttpStatus.OK.value());
    }

    @AfterAll
    void close(){
        postgres.stop();
    }
}
