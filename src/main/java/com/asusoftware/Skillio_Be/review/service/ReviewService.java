package com.asusoftware.Skillio_Be.review.service;

import com.asusoftware.Skillio_Be.appointment.repository.AppointmentRepository;
import com.asusoftware.Skillio_Be.review.model.Review;
import com.asusoftware.Skillio_Be.review.model.dto.CreateReviewDto;
import com.asusoftware.Skillio_Be.review.model.dto.ReviewDto;
import com.asusoftware.Skillio_Be.review.repository.ReviewRepository;
import com.asusoftware.Skillio_Be.service.model.ServiceEntity;
import com.asusoftware.Skillio_Be.service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;
    private final ServiceRepository serviceRepository;
    private final ModelMapper mapper;

    public ReviewDto create(CreateReviewDto dto) {
        // Găsim serviciul
        ServiceEntity service = serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new NotFoundException("Serviciul nu a fost găsit"));

        UUID providerId = service.getProviderId();

        // Verificăm dacă clientul are o programare completă cu acest provider
        boolean hasCompleted = appointmentRepository.hasCompletedAppointment(
                dto.getClientId(), providerId);

        if (!hasCompleted) {
            throw new IllegalStateException("Nu poți lăsa o recenzie fără o programare completată.");
        }

        // Verificăm dacă deja a lăsat o recenzie la acel serviciu
        boolean alreadyExists = reviewRepository.existsByClientIdAndServiceId(
                dto.getClientId(), dto.getServiceId());

        if (alreadyExists) {
            throw new IllegalStateException("Ai lăsat deja o recenzie acestui serviciu.");
        }

        Review review = Review.builder()
                .id(UUID.randomUUID())
                .serviceId(dto.getServiceId())
                .clientId(dto.getClientId())
                .rating(dto.getRating())
                .comment(dto.getComment())
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
        return mapper.map(review, ReviewDto.class);
    }

    public List<ReviewDto> getByProvider(UUID providerId) {
        List<UUID> serviceIds = serviceRepository.findIdsByProviderId(providerId);
        List<Review> reviews = reviewRepository.findByServiceIdIn(serviceIds);
        return reviews.stream().map(review -> mapper.map(review, ReviewDto.class)).toList();
    }

    public void delete(UUID reviewId) {
        reviewRepository.deleteById(reviewId);
    }
}

