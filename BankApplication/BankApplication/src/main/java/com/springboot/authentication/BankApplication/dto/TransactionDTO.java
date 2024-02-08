package com.springboot.authentication.BankApplication.dto;

import com.springboot.authentication.BankApplication.constants.TransactionMode;
import com.springboot.authentication.BankApplication.constants.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private TransactionType transactionType;

    private TransactionMode transactionMode;

    private Double amount;
}
