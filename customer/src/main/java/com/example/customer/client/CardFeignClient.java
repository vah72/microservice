package com.example.customer.client;

import com.example.customer.common.Response;
import com.example.customer.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("card")
public interface CardFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "cardDetails", consumes = "application/json")
    Response getCardDetails(@RequestHeader("correlation-id") String correlationId, @RequestBody Customer customer);
}
