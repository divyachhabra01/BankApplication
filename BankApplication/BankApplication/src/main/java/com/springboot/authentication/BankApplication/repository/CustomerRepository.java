package com.springboot.authentication.BankApplication.repository;

import com.springboot.authentication.BankApplication.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
 Customer findCustomerByUserName(String username);
 boolean existsCustomerByUserName(String username);
 boolean existsCustomerByEmail(String email);
}
