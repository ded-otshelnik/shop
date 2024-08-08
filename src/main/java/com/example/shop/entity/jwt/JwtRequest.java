package com.example.shop.entity.jwt;

import lombok.Data;

@Data
public class JwtRequest {
    private String login;
    private String password;
}
