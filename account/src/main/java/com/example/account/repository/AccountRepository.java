package com.example.account.repository;

import com.example.account.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    @Query(value = "{'username': ?0, 'branchAddress': ?1}", sort = "{username: -1, branchAddress: 1}")
    List<Account> findByUsernameAndBranchAddress(String username, String branchAddress);
    @Query(value = "{'branchAddress': ?0}",count = true)
    Integer getAccountCount(String branchAddress);
//    @Query(value = "{'username': {$regex: ?0}}", fields = "{username: 1, branchAddress: 1}")
//    List<Account> findByUsername(String username);

    Account findByUsername(String username);

    @Override
    Page<Account> findAll(Pageable pageable);
}

