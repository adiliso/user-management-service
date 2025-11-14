package com.adil.usermanagementservice.exception;

import com.adil.usermanagementservice.domain.model.dto.response.GlobalErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<GlobalErrorResponse> handleBaseException(BaseException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<GlobalErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(InvalidUpdateRequestException.class)
    public ResponseEntity<GlobalErrorResponse> handleInvalidUpdateRequestException(InvalidUpdateRequestException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public ResponseEntity<GlobalErrorResponse> handlePhoneAlreadyExistsException(PhoneAlreadyExistsException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GlobalErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        addErrorLog(ex.getCode().getHttpStatus(), ex.getMessage(), ex.getClass().getSimpleName());
        return ResponseEntity.status(Optional
                        .ofNullable(ex.getCode().getHttpStatus())
                        .orElse(HttpStatus.INTERNAL_SERVER_ERROR))
                .body(GlobalErrorResponse.builder()
                        .errorCode(ex.getCode())
                        .errorMessage(ex.getMessage())
                        .timestamp(LocalDateTime.now())
                        .requestId(UUID.randomUUID())
                        .build());
    }

    protected void addErrorLog(HttpStatus httpStatus, String errorMessage, String exceptionType) {
        int statusCode = (httpStatus != null) ? httpStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR.value();
        log.error("HTTP Status: {} | Error Message: {} | Exception Type: {}", statusCode, errorMessage, exceptionType);
    }
}