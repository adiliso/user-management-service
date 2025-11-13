package com.adil.usermanagementservice.domain.model.dto.response;

import com.adil.usermanagementservice.domain.model.enums.Role;

public record UserResponse(

        Long id,
        String name,
        String email,
        String phone,
        Role role
) {
}
