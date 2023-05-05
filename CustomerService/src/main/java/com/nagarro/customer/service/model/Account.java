package com.nagarro.customer.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {


    private String accountId;
    private String accountType;
    Date accountOpeningDate;
    Date lastActivity;
    private int balance;
    private String customerId;
    Customer customer;

}
