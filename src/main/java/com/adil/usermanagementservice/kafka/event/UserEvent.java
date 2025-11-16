package com.adil.usermanagementservice.kafka.event;

import lombok.Data;

import java.time.Instant;

@Data
public class UserEvent {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private Instant timestamp = Instant.now();
}
