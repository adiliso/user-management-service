package com.adil.usermanagementservice.domain.model.dto.request;

import com.adil.usermanagementservice.domain.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Phone is required")
        String phone,

        @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Phone number is invalid")
        Role role
) {
}

