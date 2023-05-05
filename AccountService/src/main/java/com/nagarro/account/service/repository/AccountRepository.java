package com.nagarro.account.service.repository;

import com.nagarro.account.service.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {
    List<Account> findByCustomerId(String customerId);
}
