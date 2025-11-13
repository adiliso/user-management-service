package com.adil.usermanagementservice.exception;

import com.adil.usermanagementservice.domain.model.enums.ErrorCode;

public class PhoneAlreadyExistsException extends BaseException {

    public PhoneAlreadyExistsException(String phone) {
        super("Phone already exists: " + phone, ErrorCode.ALREADY_EXISTS);
    }
}
