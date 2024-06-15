package com.example.account.controller;

import com.example.account.common.Response;
import com.example.account.dto.LoginReponse;
import com.example.account.dto.LoginRequest;
import com.example.account.dto.SignupRequest;
import com.example.account.jwt.JwtUtil;
import com.example.account.model.*;
import com.example.account.repository.AccountRepository;
import com.example.account.repository.RoleRepository;
import com.example.account.service.AccountDetailsImpl;
import com.example.account.service.client.CustomerFeignClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerFeignClient customerFeignClient;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        logger.info("Starting login ......");
        logger.info("Login request: {}", loginRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtUtil.generateJwtToken(authentication);
        logger.info("Token generated at {} : {}", new Date(), jwt);
        AccountDetailsImpl accountDetails = (AccountDetailsImpl) authentication.getPrincipal();
        List<String> roles = accountDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .toList();
        logger.info("Ending login ......");
        if (!accountDetails.isActive()){
            return ResponseEntity.badRequest()
                    .body(new Response(null, "Account is not active", 400));
        }
        return Response.response(new LoginReponse(jwt, accountDetails.getUsername(), accountDetails.getId(), roles), "success", 200);

    }

    @PostMapping("signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest){
        logger.info("Starting signup new account......");
        if (accountRepository.findByUsername(signupRequest.getUsername())!=null)
            return ResponseEntity.badRequest()
                    .body(new Response(null, "Username is already taken!", 400));

        Customer customer = createNewCustomer(signupRequest);
        if (customer == null)
            return ResponseEntity.badRequest()
                    .body(new Response(null, "Cannot create new customer information", 400));

        Account acc = new Account();
        acc.setUsername(signupRequest.getUsername());
        acc.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        acc.setBranchAddress(signupRequest.getBranchAddress());
        acc.setCustomerId(customer.getId());
        acc.setActive(true);
        //roles
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null){
            Role role = roleRepository.findByName("USER");
            if (role == null)
                throw new RuntimeException("Error : Role is not found");
            roles.add(role);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role admin = roleRepository.findByName("ROLE_ADMIN");
                        if (admin == null)
                            throw new RuntimeException("Error: Role is not found");
                        roles.add(admin);
                        break;
                    case "user":
                        Role mod = roleRepository.findByName("ROLE_USER");
                        if (mod == null)
                            throw new RuntimeException("Error : Role is not found");
                        roles.add(mod);
                        break;
                }
            });
        }
        acc.setRoles(roles);
        logger.info("Ending signup new account......");
        return Response.response(accountRepository.save(acc),  "success", 200);
    }

    private Customer createNewCustomer(SignupRequest signupRequest) {
        logger.info("Starting createNewCustomer ......");
        Customer customer = new Customer();
        customer.setEmail(signupRequest.getEmail());
        customer.setName(signupRequest.getName());
        customer.setMobileNumber(signupRequest.getMobileNumber());

        Response response = customerFeignClient.createCustomer(customer);
        logger.info("Response from customer service: {}", response);

        if (response.getStatus() == 400){
            logger.error("Error when creating new customer: {}", response.getMessage());
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        Customer _customer = mapper.convertValue(response.getData(), Customer.class);
//        Customer _customer = (Customer) response.getData();
        logger.info("Ending createNewCustomer ......");
        return response.getStatus() == 200 ? _customer : null;

    }

//
//    @PostMapping("customerDetails")
////    @CircuitBreaker(name = "customerDetails", fallbackMethod = "customerDetailsFallback")
//    public ResponseEntity<?> customerDetails(@RequestHeader("correlation-id") String correlationId, @RequestBody Customer customer) {
//        logger.info("Starting customerDetails ......");
//        Account account = accountRepository.findByCustomerId(customer.getCustomerId());
//        List<Card> cards = (List<Card>) cardFeignClient.getCardDetails(correlationId, customer).getData();
//        List<Loan> loans = (List<Loan>) loanFeignClient.getLoanDetails(correlationId, customer).getData();
//        CustomerDetails customerDetails = new CustomerDetails();
//        customerDetails.setAccount(account);
//        customerDetails.setCards(cards);
//        customerDetails.setLoans(loans);
//        logger.info("Ending customerDetails ......");
//        return Response.response(customerDetails, "success", 200);
//    }


//    @GetMapping("{username}")
//    public ResponseEntity<?> getAccountById(@PathVariable("username") String username) {
//        List<Account> account = accountRepository.findByUsername(username);
//        return Response.response(account, "success", 200);
//    }
}
