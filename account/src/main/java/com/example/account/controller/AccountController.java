package com.example.account.controller;


import com.example.account.common.Response;
import com.example.account.dto.AccountRequest;
import com.example.account.model.Account;
import com.example.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/account")
@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PutMapping("update")
    @CacheEvict(value = "accountList", allEntries = true)
    @Description("For user to update account details (in this case, only branch address)")
    public ResponseEntity<?> updateAccount(@RequestBody AccountRequest accountRequest, @RequestParam("id")String id) throws AccountNotFoundException {
        Account account = accountService.updateInformation(accountRequest, id);
        return account == null ?
                Response.response(null, "update failed", 400) :
                Response.response(account, "success", 200);
    }

    @PatchMapping("changePassword")
    @Description("For user to change password")
    public ResponseEntity<?> changePassword(@RequestParam("id") String id,
                                            @RequestParam("oldPassword") String oldPassword,
                                            @RequestParam("newPassword") String newPassword) throws AccountNotFoundException {

        if (oldPassword == null || oldPassword.isEmpty())
            return ResponseEntity.badRequest().body("Please enter old password");
        if (newPassword == null || newPassword.isEmpty())
            return ResponseEntity.badRequest().body("Please enter new password");
        if (oldPassword.equals(newPassword))
            return ResponseEntity.badRequest().body("New password cannot be the same as old password");

        String regexLength = "^.{8,}$";
        if (!newPassword.matches(regexLength))
            return ResponseEntity.badRequest().body("Password must be at least 8 characters");

        Account account = accountService.findById(id);
        if (account != null){
            if (passwordEncoder.matches(oldPassword, account.getPassword())){
                account.setPassword(passwordEncoder.encode(newPassword));
                accountService.save(account);
                return Response.response(account, "success", 200);
            } else {
                return ResponseEntity.badRequest().body("Old password is incorrect");
            }
        }
        return ResponseEntity.badRequest().body("Change password failed");
    }

    @PatchMapping("delete")
    @Description("For user to delete their own account")
    public ResponseEntity<?> deleteAccount(@RequestParam("id") String id) throws AccountNotFoundException {
        Account account = accountService.findById(id);
        if (account !=null){
            account.setActive(false);
            accountService.save(account);
            return Response.response(account, "success", 200);
        }
        return Response.response(null, "not found account id", 400);
    }

    @GetMapping("details")
    @Description("For user to view their own account details")
    public ResponseEntity<?> getAccountDetails(@RequestParam("id") String id) throws AccountNotFoundException {
        Account account = accountService.findById(id);
        return account == null ?
                Response.response(null, "Account not found", 400) :
                Response.response(account, "success", 200);
    }


    /* Admin section */

    @GetMapping("admin/list")
    @Cacheable(value = "accountList")
    @Description("For admin to view all accounts with pagination (10 accounts per page)")
    public ResponseEntity<?> getAccounts(){
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Account> pageResult = accountService.findAll(pageRequest);
        List<Account> accounts = pageResult.getContent();
        return Response.response(accounts, "success", 200);
    }

    @PatchMapping("admin/delete")
    @Description("For admin to delete accounts")
    public ResponseEntity<?> deleteAccount(@RequestParam("ids") List<String> ids) throws AccountNotFoundException {
        List<Account> accounts = new ArrayList<>();
        for (String id : ids){
            Account account = accountService.findById(id);
            if (account == null)
                return ResponseEntity.badRequest().body("Account not found with id " + id);
            account.setActive(false);
            accounts.add(account);
        }
        return Response.response(accountService.saveAll(accounts), "success", 200);
    }





}
