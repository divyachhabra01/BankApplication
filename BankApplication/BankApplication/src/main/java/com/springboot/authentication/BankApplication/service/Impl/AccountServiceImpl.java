package com.springboot.authentication.BankApplication.service.Impl;

import com.springboot.authentication.BankApplication.constants.TransactionMode;
import com.springboot.authentication.BankApplication.constants.TransactionType;
import com.springboot.authentication.BankApplication.dto.AccountDTO;
import com.springboot.authentication.BankApplication.dto.TransactionDTO;
import com.springboot.authentication.BankApplication.exception.BankAppException;
import com.springboot.authentication.BankApplication.mapper.AccountMapper;
import com.springboot.authentication.BankApplication.model.Account;
import com.springboot.authentication.BankApplication.model.Customer;
import com.springboot.authentication.BankApplication.model.Transaction;
import com.springboot.authentication.BankApplication.repository.AccountsRepository;
import com.springboot.authentication.BankApplication.repository.CustomerRepository;
import com.springboot.authentication.BankApplication.repository.TransactionRepository;
import com.springboot.authentication.BankApplication.service.AccountService;
import com.springboot.authentication.BankApplication.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountsRepository accountsRepository;
    private final AccountMapper accountMapper;

    private final CustomerRepository customerRepository;

    private final TransactionRepository transactionRepository;

    private final TransactionService transactionService;

    public AccountServiceImpl(AccountsRepository accountsRepository, AccountMapper accountMapper, CustomerRepository customerRepository, TransactionRepository transactionRepository, TransactionService transactionService) {
        this.accountsRepository = accountsRepository;
        this.accountMapper = accountMapper;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    @Override
    public Long createAccount(AccountDTO accountDTO) {
        Optional<Customer> customer = Optional.of(customerRepository.findById(accountDTO.getCustomerId()).orElseThrow());
        Account account = accountMapper.getAccount(accountDTO);
        account.setCustomer(customer.get());

        try {
            account.setAccountNumber(generateAccountNumber(account));
            accountsRepository.saveAndFlush(account);
            log.debug("Account created successfully with account id: " + account.getId());
        } catch (Exception e) {
            log.error("Getting error in account creation: {}", e.getMessage());
            throw new BankAppException("Getting error in account creation : {}" + e.getMessage(), e);
        }
        return account.getId();
    }

    @Transactional
    @Override
    public  List<Transaction>  getAccountDetailsWithTransactionHistory(Long customerId, String accountNumber) {
        Account account= accountsRepository.findByCustomerIdAndAccountNumber(customerId,accountNumber);
        List<Transaction> transactionList;
        if(account==null){
            log.error("No active account exists for given +  customer id : " + customerId + " and accountNumber: " + accountNumber);
            throw new BankAppException("No active account exists for given" + " customer id :" + customerId +"  and accountNumber: " + accountNumber);
        }
        else {
            try {
                transactionList = transactionRepository.findAllTransactionsByAccountNumber(accountNumber);
                log.debug("Transaction history list fetched successfully of size : " + transactionList.size());
            } catch (Exception e) {
                log.error("Getting error in fetching transaction history details: {} " , e.getMessage());
                throw new BankAppException("Getting error in fetching transaction history details: {} " + e.getMessage(),e);
            }
        }
        return transactionList;
    }

    @Override
    public Double getAccountBalance(Long customerId, String accountNumber) {
        Account account= accountsRepository.findByCustomerIdAndAccountNumber(customerId,accountNumber);
        if(account==null){
            log.error("No active account exists for given "+ " customer id : " + customerId + " and accountNumber: " + accountNumber);
            throw new BankAppException("No active account exists for given" + " customer id :" + customerId +"  and accountNumber: " + accountNumber);
        }
        log.debug("Account Balance : " + account.getBalance());
        return account.getBalance();
    }

    @Override
    public boolean depositAmount(Long customerId, String accountNumber, Double amount, TransactionMode mode,String description) {
        Account account= accountsRepository.findByCustomerIdAndAccountNumber(customerId,accountNumber);
        List<Transaction> history = new ArrayList<>();
        if(account==null){
            log.error("No active account exists for given "+ " customer id : " + customerId + " and accountNumber: " + accountNumber);
            throw new BankAppException("No active account exists for given" + " customer id :" + customerId +"  and accountNumber: " + accountNumber);
        }
        Double currentBalance= account.getBalance();
        account.setBalance(currentBalance+amount);
        Transaction transaction=updateTransactionHistory(account,accountNumber,amount,mode,description);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        history.add(transaction);
        account.setTransactionHistory(history);
        accountsRepository.saveAndFlush(account);
        transactionService.saveTransaction(transaction);
        log.debug("Amount deposited successfully!");
        return true;
    }

    @Override
    public boolean withdrawAmount(Long customerId, String accountNumber, Double amount,TransactionMode mode,String description) {
        Account account= accountsRepository.findByCustomerIdAndAccountNumber(customerId,accountNumber);
        if(account==null){
            log.error("No active account exists for given "+ " customer id : " + customerId + " and accountNumber: " + accountNumber);
            throw new BankAppException("No active account exists for given" + " customer id :" + customerId +"  and accountNumber: " + accountNumber);
        }
        List<Transaction> history = new ArrayList<>();
        Double currentBalance= account.getBalance();
        if(currentBalance<amount){
            return false;
        }
        account.setBalance(currentBalance-amount);
        Transaction transaction=updateTransactionHistory(account,accountNumber,amount,mode,description);
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        history.add(transaction);
        account.setTransactionHistory(history);
        accountsRepository.saveAndFlush(account);
        transactionService.saveTransaction(transaction);
        log.debug("Amount withdrawn successfully!");
        return true;
    }

    private static String generateAccountNumber(Account account){
        return account.getBranchCode() + "-" + account.getAccountType() + "-" + UUID.randomUUID().toString().substring(0,8);
    }

    private static Transaction updateTransactionHistory(Account account,String accountNumber, Double amount, TransactionMode mode,String description){
        Transaction transaction= new Transaction();
        transaction.setAmount(amount);
        transaction.setAccountNumber(accountNumber);
        transaction.setTransactionMode(mode);
        transaction.setAccountNumber(accountNumber);
        transaction.setDescription(description);
        transaction.setAccountId(account.getId());
        return transaction;
    }
}
