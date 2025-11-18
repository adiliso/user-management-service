package com.adil.usermanagementservice.common;

import com.adil.usermanagementservice.domain.entity.UserEntity;
import com.adil.usermanagementservice.domain.model.dto.request.UserCreateRequest;
import com.adil.usermanagementservice.domain.model.dto.request.UserUpdateRequest;
import com.adil.usermanagementservice.domain.model.dto.response.UserResponse;
import com.adil.usermanagementservice.domain.model.enums.Role;

import static com.adil.usermanagementservice.domain.model.enums.Role.USER;

public class UserTestConstant {

    public static final Long USER_ID = 1L;
    public static final String NAME = "TestName";
    public static final String EMAIL = "TestEmail@test.com";
    public static final String PHONE = "+994000000000";
    public static final Role ROLE = USER;

    public static UserCreateRequest getUserCreateRequest() {
        return new UserCreateRequest(
                NAME,
                EMAIL,
                PHONE,
                ROLE
        );
    }

    public static UserResponse getUserResponse() {
        return new UserResponse(
                USER_ID,
                NAME,
                EMAIL,
                PHONE,
                ROLE
        );
    }

    public static UserEntity getUserEntity() {
        return UserEntity.builder()
                .id(USER_ID)
                .name(NAME)
                .email(EMAIL)
                .phone(PHONE)
                .role(ROLE)
                .build();
    }

    public static UserUpdateRequest getUserUpdateRequest() {
        return UserUpdateRequest.builder()
                .name(NAME)
                .email(EMAIL)
                .phone(PHONE)
                .role(ROLE)
                .build();
    }
}
