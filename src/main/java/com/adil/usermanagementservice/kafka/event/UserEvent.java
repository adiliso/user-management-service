package com.adil.usermanagementservice.kafka.event;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UserEvent {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
    @Builder.Default
    private Instant timestamp = Instant.now();
}
