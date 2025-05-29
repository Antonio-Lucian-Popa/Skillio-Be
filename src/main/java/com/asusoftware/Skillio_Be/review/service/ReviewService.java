package com.asusoftware.Skillio_Be.review.service;

import com.asusoftware.Skillio_Be.appointment.repository.AppointmentRepository;
import com.asusoftware.Skillio_Be.review.model.Review;
import com.asusoftware.Skillio_Be.review.model.dto.CreateReviewDto;
import com.asusoftware.Skillio_Be.review.model.dto.ReviewDto;
import com.asusoftware.Skillio_Be.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper mapper;

    public ReviewDto create(CreateReviewDto dto) {
        boolean valid = appointmentRepository.existsByClientIdAndServiceIdAndStatus(
                dto.getClientId(), dto.getServiceId(), "COMPLETED");

        if (!valid) {
            throw new RuntimeException("Nu poți lăsa review fără o programare completată.");
        }

        Review review = Review.builder()
                .clientId(dto.getClientId())
                .serviceId(dto.getServiceId())
                .rating(dto.getRating())
                .comment(dto.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        return mapper.map(reviewRepository.save(review), ReviewDto.class);
    }
}

