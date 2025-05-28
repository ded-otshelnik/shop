package com.example.shop.controller;

import com.example.shop.entity.Review;
import com.example.shop.entity.dto.ReviewDto;
import com.example.shop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;


    @GetMapping("/{product_id}/reviews")
    public List<Review> getReviews(@PathVariable("product_id") Long productId){
        return reviewService.getReviews(productId);
    }

    @PostMapping("/add-review")
    public void addReview(ReviewDto reviewDto){
        reviewService.addReview(reviewDto);
    }

    @DeleteMapping("/remove-review")
    public void removeReview(Long reviewId){
        reviewService.removeReview(reviewId);
    }
}
