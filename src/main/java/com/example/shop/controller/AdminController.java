package com.example.shop.controller;

import java.util.List;

import com.example.shop.service.AdminService;
import com.example.shop.entity.User;
import com.example.shop.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/admin")
@AllArgsConstructor
@Slf4j
@Tag(name = "AdminController", description = "Admin controller")
@Secured("ADMIN")
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;

    @GetMapping("get-users")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Get users",
            description = "Get all users"
    )
    @ApiResponse(responseCode = "200")
    private List<User> getUsers(){
        return userService.getUsers();
    }

    @PutMapping("add-admin")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Add new admin",
            description = "Grant admin role to user"
    )
    @ApiResponse(responseCode = "403")
    @ApiResponse(responseCode = "200")
    private void addNewAdmin(@NonNull String username){
        adminService.grantAdminAuthority(username);
        log.info("Granted new admin role to user {}", username);
    }

}
