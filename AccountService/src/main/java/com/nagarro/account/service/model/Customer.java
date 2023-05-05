package com.nagarro.account.service.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Customer {



    private String customerId;

    private String customerName;

    private String customerPhone;

    private String customerEmail;

    private String customerPermanentAddress;

    private String customerIdentityDocument;
}
