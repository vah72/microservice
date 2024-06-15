package com.example.account.service;

import com.example.account.dto.AccountRequest;
import com.example.account.model.Account;
import com.example.account.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account acc = accountRepository.findByUsername(username);
        if ( acc == null){
            throw  new UsernameNotFoundException("Not Found account with username");
        }
        return AccountDetailsImpl.build(acc);
    }

    @Override
    public Account updateInformation(AccountRequest accountRequest, String id) throws AccountNotFoundException {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null){
            account.setBranchAddress(accountRequest.getBranchAdress());
            accountRepository.save(account);
            return account;
        }
        return null;
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account findById(String id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public List<Account> saveAll(List<Account> accounts) {
        return accountRepository.saveAll(accounts);
    }


}
