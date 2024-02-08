package com.springboot.authentication.BankApplication.service.Impl;

import com.springboot.authentication.BankApplication.model.Transaction;
import com.springboot.authentication.BankApplication.repository.TransactionRepository;
import com.springboot.authentication.BankApplication.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.saveAndFlush(transaction);
    }
}
