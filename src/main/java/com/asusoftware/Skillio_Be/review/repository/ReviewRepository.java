package com.asusoftware.Skillio_Be.review.repository;

import com.asusoftware.Skillio_Be.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
}
