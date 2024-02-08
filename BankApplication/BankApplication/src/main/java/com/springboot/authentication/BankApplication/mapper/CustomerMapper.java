package com.springboot.authentication.BankApplication.mapper;

import com.springboot.authentication.BankApplication.dto.CustomerDTO;
import com.springboot.authentication.BankApplication.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    Customer getCustomer(CustomerDTO customerDTO);

}
