package com.nagarro.account.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name="Account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private String accountId;

    private String accountType;

    @DateTimeFormat
    Date accountOpeningDate;

    @DateTimeFormat
    Date lastActivity;
    private int balance;

    private String customerId;

    @Transient
    Customer customer;

}
