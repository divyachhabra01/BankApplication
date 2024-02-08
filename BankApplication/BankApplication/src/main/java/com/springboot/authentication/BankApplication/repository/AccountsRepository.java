package com.springboot.authentication.BankApplication.repository;

import com.springboot.authentication.BankApplication.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AccountsRepository extends JpaRepository<Account,Long> {
    Account findByCustomerIdAndAccountNumber(Long customerId, String accountNumber);
    Set<Account> findAllAccountsByCustomerId(Long customerId);
}
