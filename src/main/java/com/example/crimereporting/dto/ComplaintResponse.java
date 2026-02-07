package com.example.crimereporting.dto;

import java.time.OffsetDateTime;

public record ComplaintResponse(
    Long id,
    String title,
    String description,
    String status,
    OffsetDateTime createdAt,
    String submittedBy
) {
}
