package com.springboot.authentication.BankApplication.mapper;

import com.springboot.authentication.BankApplication.dto.AccountDTO;
import com.springboot.authentication.BankApplication.dto.TransactionDTO;
import com.springboot.authentication.BankApplication.model.Account;
import com.springboot.authentication.BankApplication.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    AccountDTO getAccountDTO(Account account);
    Account getAccount(AccountDTO accountDTO);

}
