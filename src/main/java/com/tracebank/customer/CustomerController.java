package com.tracebank.customer;

import com.tracebank.customer.dto.CustomerRequest;
import com.tracebank.customer.dto.CustomerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }
}