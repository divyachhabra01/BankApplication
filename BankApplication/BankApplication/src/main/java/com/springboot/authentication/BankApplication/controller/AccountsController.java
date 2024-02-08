package com.springboot.authentication.BankApplication.controller;

import com.springboot.authentication.BankApplication.constants.TransactionMode;
import com.springboot.authentication.BankApplication.constants.TransactionType;
import com.springboot.authentication.BankApplication.dto.AccountDTO;
import com.springboot.authentication.BankApplication.model.Transaction;
import com.springboot.authentication.BankApplication.service.AccountService;
import com.springboot.authentication.BankApplication.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "api/v1")
public class AccountsController {
    private final AccountService accountService;

    public AccountsController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping(value = "/account", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAccount(@RequestBody @Validated AccountDTO accountDTO){
        ResponseEntity<?> response=null;
        Long accountId= accountService.createAccount(accountDTO);
        if(accountId>0){
            log.debug("Account created successfully with given account id :"  + accountId);
            response=ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully with given account id : " + accountId);
        }
        return response;
    }

    @GetMapping(value = "/{customerId}/accounts/{accountNumber}/transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable("customerId") Long customerId, @PathVariable("accountNumber") String accountNumber){
        List<Transaction> transactionList= accountService.getAccountDetailsWithTransactionHistory(customerId,accountNumber);
        if(transactionList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transactionList);
    }

    @GetMapping(value = "/{customerId}/accounts/{accountNumber}/balance",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getAccountBalance(@PathVariable("customerId") Long customerId, @PathVariable("accountNumber") String accountNumber){
      Double balance = accountService.getAccountBalance(customerId,accountNumber);
      return ResponseEntity.ok(balance);
    }

    @PostMapping(value = "{customerId}/accounts/{accountNumber}/deposit")
    public ResponseEntity<String> depositAmount(@PathVariable("customerId") Long customerId, @PathVariable("accountNumber") String accountNumber, @RequestParam Double amount , @RequestParam TransactionMode transactionMode,@RequestParam String description){
        boolean response= accountService.depositAmount(customerId,accountNumber,amount,transactionMode,description);
        if(response){
            return ResponseEntity.ok("Amount Deposited Successfully !");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Oops ! Transaction Failed ");

    }

    @PostMapping(value = "{customerId}/accounts/{accountNumber}/withdraw")
    public ResponseEntity<String> withdrawAmount(@PathVariable("customerId") Long customerId,@PathVariable("accountNumber") String accountNumber,@RequestParam Double amount,@RequestParam TransactionMode transactionMode,@RequestParam String description){
        boolean response= accountService.withdrawAmount(customerId,accountNumber,amount,transactionMode,description);
        if(response){
            return ResponseEntity.ok("Amount Withdraw Successfully !");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Withdraw operation failed account doesn't have sufficient balance");
        }
    }


}
