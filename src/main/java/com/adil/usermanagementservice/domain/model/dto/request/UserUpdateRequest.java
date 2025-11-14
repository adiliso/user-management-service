package com.adil.usermanagementservice.domain.model.dto.request;

import com.adil.usermanagementservice.domain.model.enums.Role;
import com.adil.usermanagementservice.exception.InvalidUpdateRequestException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(

        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,

        @Email(message = "Email must be valid")
        String email,

        @Pattern(regexp = "^\\+?[0-9]{8,15}$", message = "Phone number must be valid")
        String phone,

        Role role
) {
    public UserUpdateRequest {
        if (name == null && email == null && phone == null && role == null) {
            throw new InvalidUpdateRequestException("At least one field must be provided for update");
        }
    }
}