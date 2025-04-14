package com.example.shop.entity.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private Long productId;
    private String reviewText;
}
