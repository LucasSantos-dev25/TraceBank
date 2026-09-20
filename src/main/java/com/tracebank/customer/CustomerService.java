package com.tracebank.customer;

import com.tracebank.customer.dto.CustomerRequest;
import com.tracebank.customer.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Transactional
    public CustomerResponse create(CustomerRequest request) {
        if (customerRepository.existsByCpf(request.cpf())) {
            throw new CpfAlreadyRegisteredException(request.cpf());
        }
        Customer customer = customerMapper.toEntity(request);
        Customer saved = customerRepository.save(customer);
        return customerMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return customerMapper.toResponse(customer);
    }

    @Transactional(readOnly = true)
    public Customer findEntityById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }
}