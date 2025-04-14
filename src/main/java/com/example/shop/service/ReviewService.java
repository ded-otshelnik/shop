package com.example.shop.service;

import com.example.shop.entity.Product;
import com.example.shop.entity.Review;
import com.example.shop.entity.dto.ReviewDto;
import com.example.shop.exception.ResourceNotFoundException;
import com.example.shop.repo.ProductRepository;
import com.example.shop.repo.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void addReview(ReviewDto reviewDto){
        Product product = productRepository.findById(reviewDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No such product with id %d", reviewDto.getProductId())));

        Review review = new Review(reviewDto.getReviewText(), product);

        product.addReview(review);
        productRepository.save(product);
    }

    @Transactional
    public void removeReview(Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No such review with id %d", reviewId)));

        reviewRepository.delete(review);
    }

    public List<Review> getReviews(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("No such product with id %d", productId)))
                .getReviews();
    }
}
