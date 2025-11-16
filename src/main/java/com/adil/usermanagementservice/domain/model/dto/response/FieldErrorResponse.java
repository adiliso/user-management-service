package com.adil.usermanagementservice.domain.model.dto.response;

public record FieldErrorResponse(

        String field,
        String message
) {
}
