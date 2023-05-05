package com.nagarro.customer.service.service;

import com.nagarro.customer.service.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    Customer save(Customer customer);

    void deleteById(String id);

    Optional<Customer> findById(String id);

    List <Customer> findAll();
}
