package com.nagarro.customer.service.model;

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
@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @Column
    private String customerId;
    @Column
    private String customerName;
    @Column
    private String customerPhone;
    @Column
    private String customerEmail;

    @Column
    private String customerPermanentAddress;
    @Column
    private String customerIdentityDocument;



}
