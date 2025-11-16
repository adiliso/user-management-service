package com.adil.usermanagementservice.domain.model.dto.response;

import com.adil.usermanagementservice.domain.model.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalErrorResponse implements Serializable {

    private UUID requestId;
    private ErrorCode errorCode;
    private String errorMessage;
    private List<FieldErrorResponse> errors;
    private LocalDateTime timestamp;
}
