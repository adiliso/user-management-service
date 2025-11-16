package com.adil.usermanagementservice.domain.model.dto;

import com.adil.usermanagementservice.domain.model.enums.LogLevel;

import java.time.Instant;

public record LogMessageDto(

        LogLevel level,
        String message,
        Instant timestamp
) {
}