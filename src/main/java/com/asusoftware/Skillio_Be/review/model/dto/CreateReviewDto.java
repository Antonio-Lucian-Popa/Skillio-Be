package com.asusoftware.Skillio_Be.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDto {
    private UUID serviceId;
    private UUID clientId;
    private Integer rating;
    private String comment;
}