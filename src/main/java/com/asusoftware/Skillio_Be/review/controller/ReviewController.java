package com.asusoftware.Skillio_Be.review.controller;

import com.asusoftware.Skillio_Be.review.model.dto.CreateReviewDto;
import com.asusoftware.Skillio_Be.review.model.dto.ReviewDto;
import com.asusoftware.Skillio_Be.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Creează o recenzie nouă – doar dacă clientul a avut o programare COMPLETĂ cu acel provider.
     */
    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestBody CreateReviewDto dto) {
        ReviewDto created = reviewService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Returnează toate recenziile pentru un provider.
     */
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ReviewDto>> getByProvider(@PathVariable UUID providerId) {
        return ResponseEntity.ok(reviewService.getByProvider(providerId));
    }

    /**
     * Șterge o recenzie (ex: admin).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

