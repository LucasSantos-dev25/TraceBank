package com.tracebank.customer;

import com.tracebank.customer.dto.CustomerRequest;
import com.tracebank.customer.dto.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequest request) {
        return Customer.builder()
                .name(request.name())
                .cpf(request.cpf())
                .birthDate(request.birthDate())
                .build();
    }

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getBirthDate()
        );
    }
}