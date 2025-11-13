package com.adil.usermanagementservice.exception;

import com.adil.usermanagementservice.domain.model.enums.ErrorCode;

public class EmailAlreadyExistsException extends BaseException {

    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email, ErrorCode.ALREADY_EXISTS);
    }
}
