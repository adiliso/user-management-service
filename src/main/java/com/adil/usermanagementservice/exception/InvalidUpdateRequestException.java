package com.adil.usermanagementservice.exception;

import com.adil.usermanagementservice.domain.model.enums.ErrorCode;

public class InvalidUpdateRequestException extends BaseException {

    public InvalidUpdateRequestException(String message) {
        super(message, ErrorCode.VALIDATION_FAILED);
    }
}
