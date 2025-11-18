package com.adil.usermanagementservice.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
