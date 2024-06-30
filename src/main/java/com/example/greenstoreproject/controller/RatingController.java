package com.example.greenstoreproject.controller;

import com.example.greenstoreproject.bean.request.rating.RatingRequest;
import com.example.greenstoreproject.bean.response.rating.RatingResponse;
import com.example.greenstoreproject.entity.Rating;
import com.example.greenstoreproject.entity.Review;
import com.example.greenstoreproject.service.RatingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rating")
@SecurityRequirement(name = "bearerAuth")
public class RatingController {
    private final RatingService ratingService;

    @PostMapping
    public String addRating(@RequestBody RatingRequest ratingRequest) {
        return ratingService.createRating(ratingRequest);
    }

    @GetMapping("/product/{productId}")
    public List<RatingResponse> getRatingByProductId(@PathVariable Long productId) {
        return ratingService.getRatingByProductId(productId);
    }
}
