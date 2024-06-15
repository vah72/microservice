package com.example.account.service.client;

import com.example.account.common.Response;
import com.example.account.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("customer")
public interface CustomerFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/customer/signup", consumes = "application/json")
    Response createCustomer(@RequestBody Customer customer);

}
