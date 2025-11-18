package com.adil.usermanagementservice.exception;

import com.adil.usermanagementservice.domain.model.enums.ErrorCode;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException(Long id) {
            super(String.format("User with id %d was not found", id), ErrorCode.USER_NOT_FOUND);
    }
}
