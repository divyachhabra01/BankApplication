package com.springboot.authentication.BankApplication.service;

import com.springboot.authentication.BankApplication.dto.CustomerDTO;
import com.springboot.authentication.BankApplication.model.Account;
import com.springboot.authentication.BankApplication.model.Customer;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CustomerService {
    Long userRegistration(CustomerDTO customerDTO);
    Optional<Customer> getLoggedInUserDetails(String userName);

    Set<Account> getCustomerAccounts(Long customerId);
}
