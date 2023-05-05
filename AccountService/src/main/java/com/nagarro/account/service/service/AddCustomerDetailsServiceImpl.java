package com.nagarro.account.service.service;

import com.nagarro.account.service.model.Account;
import com.nagarro.account.service.model.Customer;
import com.nagarro.account.service.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AddCustomerDetailsServiceImpl implements AddCustomerDetailsService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void generateCustomerDetails() {
        Object customerObj = restTemplate.getForObject("http://CUSTOMER-SERVICE/customers", Object.class);

// Extract the customer data from the response
        Map<String, Object> responseMap = (Map<String, Object>) customerObj;
        List<Map<String, String>> customerList = (List<Map<String, String>>) responseMap.get("data");

// Retrieve the accounts from the database
        List<Account> accounts = accountRepository.findAll();

// Loop through the accounts and insert the customer data
        for (Account account : accounts) {
            // Find the matching customer data based on the customerId
            Map<String, String> customerData = customerList.stream()
                    .filter(c -> c.get("customerId").equals(account.getCustomerId()))
                    .findFirst()
                    .orElse(null);

            // If customer data is found, insert it into the account's customer field
            if (customerData != null) {
                Customer customer = new Customer();
                customer.setCustomerId(customerData.get("customerId"));
                customer.setCustomerName(customerData.get("customerName"));
                customer.setCustomerPhone(customerData.get("customerPhone"));
                customer.setCustomerEmail(customerData.get("customerEmail"));
                customer.setCustomerPermanentAddress(customerData.get("customerPermanentAddress"));
                customer.setCustomerIdentityDocument(customerData.get("customerIdentityDocument"));
                account.setCustomer(customer);
            }
        }

// Save the updated accounts to the database
        accountRepository.saveAll(accounts);
    }
}
