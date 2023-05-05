package com.nagarro.account.service.service;

import com.nagarro.account.service.exceptions.ResourceNotFoundException;
import com.nagarro.account.service.model.Account;
import com.nagarro.account.service.model.Customer;
import com.nagarro.account.service.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AddCustomerDetailsService addCustomerDetailsService;

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);


    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Account create(Account account) {
        String accountId = UUID.randomUUID().toString();
        account.setAccountId(accountId);


        Date current_Date = new Date();

        account.setAccountOpeningDate(current_Date);
        account.setLastActivity(current_Date);

        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAccounts() {

        addCustomerDetailsService.generateCustomerDetails();
        return accountRepository.findAll();
    }

    @Override
    public Account getAccount(String id) {

//        // Getting Accounts from ACCOUNT SERVICE
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with given id not found try again with correct details !!"));

        addCustomerDetailsService.generateCustomerDetails();
        return account;
    }

    @Override
    public List<Account> getAccountByCustomerId(String customerId) {

        addCustomerDetailsService.generateCustomerDetails();
        return accountRepository.findByCustomerId(customerId);
    }


    @Override
    public Account updateAccount(String id, Account account) {

        Account newAccount = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with given id not found  try again with correct details!!"));
        newAccount.setAccountType(account.getAccountType());
        newAccount.setBalance(account.getBalance());
        newAccount.setLastActivity(new Date());
        return accountRepository.save(newAccount);
    }

    @Override
    public Account addBalance(String id, int amount, String customerId) {


        Account newAccount = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with given id not found try again with correct details !!"));

        Customer customer = restTemplate.getForObject("http://CUSTOMER-SERVICE/customers/" + customerId, Customer.class);

        if (customer == null) {

            throw new ResourceNotFoundException("Customer with given id not found try again with correct details !!");
        } else {

            int newBalance = newAccount.getBalance() + amount;
            newAccount.setBalance(newBalance);
            newAccount.setLastActivity(new Date());

            return accountRepository.save(newAccount);
        }


    }

    @Override
    public Account withdrawBalance(String id, int amount, String customerId) {


        Account newAccount = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with given id not found try again with correct details !!"));

        Customer customer = restTemplate.getForObject("http://CUSTOMER-SERVICE/customers/" + customerId, Customer.class);

        if (customer == null) {
            throw new ResourceNotFoundException("Customer with given id not found try again with correct details !!");
        } else {

            int newBalance = newAccount.getBalance() - amount;
            newAccount.setBalance(newBalance);
            newAccount.setLastActivity(new Date());
            return accountRepository.save(newAccount);
        }



    }


    @Override
    public void delete(String id) {

        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with given id not found !!"));
        this.accountRepository.delete(account);

    }

    @Override
    public void deleteAccountUsingCustomerId(String customerId) {

        List<Account> accounts = accountRepository.findByCustomerId(customerId);

        for( Account account : accounts)
        {
            this.accountRepository.delete(account);
        }
    }
}
