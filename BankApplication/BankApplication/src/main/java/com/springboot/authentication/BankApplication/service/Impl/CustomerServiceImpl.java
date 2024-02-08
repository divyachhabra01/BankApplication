package com.springboot.authentication.BankApplication.service.Impl;

import com.springboot.authentication.BankApplication.dto.CustomerDTO;
import com.springboot.authentication.BankApplication.exception.BankAppException;
import com.springboot.authentication.BankApplication.mapper.CustomerMapper;
import com.springboot.authentication.BankApplication.model.Account;
import com.springboot.authentication.BankApplication.model.Customer;
import com.springboot.authentication.BankApplication.repository.AccountsRepository;
import com.springboot.authentication.BankApplication.repository.CustomerRepository;
import com.springboot.authentication.BankApplication.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    private final AccountsRepository accountsRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper mapper, AccountsRepository accountsRepository) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        this.accountsRepository = accountsRepository;
    }

    @Override
    public Long userRegistration(CustomerDTO customerDTO) {
        Customer customer = mapper.getCustomer(customerDTO);
        Customer savedCustomer;
        String username = customer.getUserName();
        String email = customer.getEmail();
        if (customerRepository.existsCustomerByUserName(username) || customerRepository.existsCustomerByEmail(email)) {
            log.error("User already exists with the given credentials" + " username : " + username + " email : " + email);
            throw new BankAppException("User already exists with the given credentials !");
        } else {
            try {
                savedCustomer = customerRepository.saveAndFlush(customer);
                log.debug("User registered successfully with user id: " + savedCustomer.getId());
            } catch (Exception e) {
                log.error("Getting error in user registration: {}", e.getMessage());
                throw new BankAppException("Getting error in user registration: {}" + e.getMessage(), e);

            }
        }
        return savedCustomer.getId();
    }

    @Override
    public Optional<Customer> getLoggedInUserDetails(String userName) {
        Optional<Customer> loggedInCustomerDetails = Optional.ofNullable(customerRepository.findCustomerByUserName(userName));
        if (loggedInCustomerDetails.isPresent()) {
            log.debug("Logged in user details fetched successfully!");
            return loggedInCustomerDetails;
        } else {
            throw new BankAppException("No user exists with given username :  " + userName);
        }
    }

    @Override
    public Set<Account> getCustomerAccounts(Long customerId) {
        Set<Account> accountList;
        try {
            accountList = accountsRepository.findAllAccountsByCustomerId(customerId);
            log.debug("Found {} accounts for customer ID: {}", accountList.size(), customerId);
        } catch (Exception e) {
            log.error("Getting error in fetching account details for customer id : {}" + customerId, e.getMessage());
            throw new BankAppException("Getting error in fetching account details for customer id : {}" + customerId + e.getMessage(), e);
        }
        if (!accountList.isEmpty()) {
            return accountList;

        } else {
            throw new BankAppException("No account associated with given user id : " + customerId);
        }
    }
}