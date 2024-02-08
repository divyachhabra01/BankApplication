package com.springboot.authentication.BankApplication.dto;
import com.springboot.authentication.BankApplication.constants.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private AccountType accountType;
    private Double balance;
    private Long customerId;
    private String branchCode;
}
