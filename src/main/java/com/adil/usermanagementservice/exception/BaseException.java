package com.adil.usermanagementservice.exception;

import com.adil.usermanagementservice.domain.model.enums.ErrorCode;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode code;

    public BaseException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }
}
