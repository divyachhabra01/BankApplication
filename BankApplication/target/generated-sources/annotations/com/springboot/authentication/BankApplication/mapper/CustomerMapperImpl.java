package com.springboot.authentication.BankApplication.mapper;

import com.springboot.authentication.BankApplication.dto.CustomerDTO;
import com.springboot.authentication.BankApplication.model.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-08T21:34:16+0530",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer getCustomer(CustomerDTO customerDTO) {
        if ( customerDTO == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setUserName( customerDTO.getUserName() );
        customer.setEmail( customerDTO.getEmail() );
        customer.setMobileNumber( customerDTO.getMobileNumber() );
        customer.setPassword( customerDTO.getPassword() );
        customer.setRole( customerDTO.getRole() );

        return customer;
    }
}
