package com.springboot.authentication.BankApplication.mapper;

import com.springboot.authentication.BankApplication.dto.AccountDTO;
import com.springboot.authentication.BankApplication.dto.TransactionDTO;
import com.springboot.authentication.BankApplication.model.Account;
import com.springboot.authentication.BankApplication.model.Transaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-08T21:31:49+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDTO getAccountDTO(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDTO accountDTO = new AccountDTO();

        accountDTO.setAccountType( account.getAccountType() );
        accountDTO.setBalance( account.getBalance() );
        accountDTO.setCustomerId( account.getCustomerId() );
        accountDTO.setBranchCode( account.getBranchCode() );

        return accountDTO;
    }

    @Override
    public TransactionDTO getTransactionDTO(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionDTO transactionDTO = new TransactionDTO();

        transactionDTO.setTransactionType( transaction.getTransactionType() );
        transactionDTO.setTransactionMode( transaction.getTransactionMode() );
        transactionDTO.setAmount( transaction.getAmount() );

        return transactionDTO;
    }

    @Override
    public Account getAccount(AccountDTO accountDTO) {
        if ( accountDTO == null ) {
            return null;
        }

        Account account = new Account();

        if ( accountDTO.getCustomerId() != null ) {
            account.setCustomerId( accountDTO.getCustomerId() );
        }
        account.setAccountType( accountDTO.getAccountType() );
        account.setBalance( accountDTO.getBalance() );
        account.setBranchCode( accountDTO.getBranchCode() );

        return account;
    }
}
