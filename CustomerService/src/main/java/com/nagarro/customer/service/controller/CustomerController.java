package com.nagarro.customer.service.controller;

import com.nagarro.customer.service.constants.Constant;
import com.nagarro.customer.service.model.Customer;
import com.nagarro.customer.service.response.ResponseHandler;
import com.nagarro.customer.service.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<Object> getAllCustomers() {
        try {
            List<Customer> customers = customerService.findAll();

            if (customers.isEmpty() || customers.size() == 0) {
                return ResponseHandler.generateResponse(Constant.NO_DATA_MESSAGE, HttpStatus.NO_CONTENT, null);
            }
            return ResponseHandler.generateResponse(Constant.SUCCESS_GET_MESSAGE, HttpStatus.OK, customers);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<Object> getCustomer(@PathVariable String id) {
        try {
            Optional<Customer> searchedCustomer = customerService.findById(id);
            if (!searchedCustomer.isPresent()) {
                return ResponseHandler.generateResponse(Constant.NO_DATA_MESSAGE, HttpStatus.NO_CONTENT, null);

            }
            return ResponseHandler.generateResponse(Constant.SUCCESS_GET_MESSAGE, HttpStatus.OK, searchedCustomer.get());


        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }


    @PostMapping("/customers")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer)
    {

        System.out.println(customer);
        try {
            System.out.println(customer);
            Optional<Customer> existCustomer = customerService.findById(customer.getCustomerId());

            System.out.println(existCustomer);

            if (!existCustomer.isPresent()) {
                Customer savedCustomer = customerService.save(customer);
                System.out.println(savedCustomer);
                if (savedCustomer != null) {
                    return ResponseHandler.generateResponse(Constant.SUCCESS_ADD_MESSAGE, HttpStatus.CREATED, savedCustomer);
                }
            }
            return ResponseHandler.generateResponse(Constant.CONFLICT_MESSAGE_CUSTOMER_EXIST, HttpStatus.CONFLICT, null);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse(Constant.CONFLICT_MESSAGE, HttpStatus.CONFLICT, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Object> updateCustomer(@RequestBody Customer customer, @PathVariable String id) {
        try {
            Optional<Customer> existCustomer = customerService.findById(id);
            if (existCustomer.isPresent()) {
                Customer searchedCustomer = existCustomer.get();
                searchedCustomer.setCustomerName(customer.getCustomerName());
                searchedCustomer.setCustomerPermanentAddress(customer.getCustomerPermanentAddress());
                searchedCustomer.setCustomerIdentityDocument(customer.getCustomerIdentityDocument());

                Customer updatedCustomer = customerService.save(searchedCustomer);
                return ResponseHandler.generateResponse(Constant.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK, updatedCustomer);
            }
            return ResponseHandler.generateResponse(Constant.CONFLICT_MESSAGE_CUSTOMER_NOT_PRESENT, HttpStatus.CONFLICT, null);
        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse(Constant.CONFLICT_MESSAGE, HttpStatus.CONFLICT, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable String id) {
        try {
            Optional<Customer> deletedCustomer = customerService.findById(id);

            if (deletedCustomer.isPresent()) {
                customerService.deleteById(id);
                return ResponseHandler.generateResponse(Constant.DELETE_SUCCESS_MESSAGE, HttpStatus.OK, deletedCustomer);
            }
            return ResponseHandler.generateResponse(Constant.NO_DATA_MESSAGE, HttpStatus.CONFLICT, null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}




