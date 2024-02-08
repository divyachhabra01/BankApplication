package com.springboot.authentication.BankApplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.authentication.BankApplication.constants.TransactionMode;
import com.springboot.authentication.BankApplication.constants.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Entity
@Table
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String accountNumber;
    @Column
    @CreationTimestamp
    private LocalDateTime transactionDate;

    @Column
    private String description;

    @Column(name = "account_id")
    private Long accountId;

    @Column
    private Double amount;
    @Column
    @Enumerated(EnumType.STRING)
    private TransactionMode transactionMode;

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Account account;
}
