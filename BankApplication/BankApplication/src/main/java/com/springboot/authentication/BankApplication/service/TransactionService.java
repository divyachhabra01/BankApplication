package com.springboot.authentication.BankApplication.service;

import com.springboot.authentication.BankApplication.model.Transaction;

public interface TransactionService {
    void saveTransaction(Transaction transaction);
}
