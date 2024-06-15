package com.example.account.service;


import com.example.account.dto.AccountRequest;
import com.example.account.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface AccountService extends UserDetailsService {

    Account updateInformation(AccountRequest accountRequest, String id) throws AccountNotFoundException;
    Page<Account> findAll(Pageable pageable);
    Account save(Account account);
    Account findById(String id) throws AccountNotFoundException;
    Account findByUsername(String username);
    List<Account> saveAll(List<Account> accounts);




}
