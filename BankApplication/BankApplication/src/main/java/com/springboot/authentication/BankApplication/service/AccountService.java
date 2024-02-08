package com.springboot.authentication.BankApplication.service;

import com.springboot.authentication.BankApplication.constants.TransactionMode;
import com.springboot.authentication.BankApplication.constants.TransactionType;
import com.springboot.authentication.BankApplication.dto.AccountDTO;
import com.springboot.authentication.BankApplication.model.Account;
import com.springboot.authentication.BankApplication.model.Transaction;

import java.util.List;

public interface AccountService {
   Long createAccount(AccountDTO accountDTO);
   List<Transaction> getAccountDetailsWithTransactionHistory(Long customerId, String accountNumber);

   Double getAccountBalance(Long customerId, String accountNumber);

   boolean depositAmount(Long customerId, String accountNumber, Double amount, TransactionMode mode,String description);

   boolean withdrawAmount(Long customerId, String accountNumber,Double amount,TransactionMode mode,String description);
}
