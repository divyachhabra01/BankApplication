package com.springboot.authentication.BankApplication.repository;

import com.springboot.authentication.BankApplication.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllTransactionsByAccountNumber(String accountNumber);
}
