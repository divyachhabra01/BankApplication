package com.springboot.authentication.BankApplication.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@Builder
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BankAppException extends RuntimeException {
    @NonNull
    private String message;
    private Throwable cause;
}
