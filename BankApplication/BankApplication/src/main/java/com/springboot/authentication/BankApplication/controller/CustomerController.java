package com.springboot.authentication.BankApplication.controller;

import com.springboot.authentication.BankApplication.model.Account;
import com.springboot.authentication.BankApplication.model.Customer;
import com.springboot.authentication.BankApplication.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value ="api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/current",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Customer>> getLoggedInUserDetails(Authentication authentication){
        log.debug("Logged in user details fetched successfully!" );
        return ResponseEntity.ok(customerService.getLoggedInUserDetails(authentication.getName()));

    }

    @GetMapping(value = "/{customerId}/accounts",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<Account>> getCustomerAccounts( @PathVariable (value = "customerId") Long customerId){
    return ResponseEntity.ok(customerService.getCustomerAccounts(customerId));

    }
}
