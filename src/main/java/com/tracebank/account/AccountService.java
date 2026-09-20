package com.tracebank.account;

import com.tracebank.customer.Customer;
import com.tracebank.customer.CustomerService;
import com.tracebank.account.dto.AccountRequest;
import com.tracebank.account.dto.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerService customerService;

    @Transactional
    public AccountResponse create(Long customerId, AccountRequest request) {
        Customer customer = customerService.findEntityById(customerId);

        Account account = Account.builder()
                .number(generateAccountNumber())
                .type(request.type())
                .balance(BigDecimal.ZERO)
                .status(AccountStatus.ACTIVE)
                .openedAt(LocalDateTime.now())
                .customer(customer)
                .build();

        Account saved = accountRepository.save(account);
        return accountMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AccountResponse findById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
        return accountMapper.toResponse(account);
    }

    private String generateAccountNumber() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}