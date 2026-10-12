package com.tracebank.customer;

import com.tracebank.customer.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    @ApiFindCustomer
    @IsCustomerOwner
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.findById(customerId));
    }
}