package com.nagarro.customer.service.service;

import com.nagarro.customer.service.model.Customer;
import com.nagarro.customer.service.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteById(String id) {
//        Object accountObj = restTemplate.getForObject("http://localhost:8082/account/user/C1" , Object.class);

        Object accountObj = restTemplate.getForObject("http://ACCOUNT-SERVICE/accounts/user/"+id, Object.class);

        if(accountObj != null)
        {
            List<Map<String, Object>> accounts = (List<Map<String, Object>>) accountObj;

            for (Map<String, Object> account : accounts) {
                String accountId = account.get("accountId").toString();
                restTemplate.delete("http://ACCOUNT-SERVICE/accounts/" + accountId);
            }
            customerRepository.deleteById(id);
            return;
        }
        customerRepository.deleteById(id);
    }

    @Override
    public Optional<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
