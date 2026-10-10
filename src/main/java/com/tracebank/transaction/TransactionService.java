package com.tracebank.transaction;

import com.tracebank.account.Account;
import com.tracebank.account.AccountService;
import com.tracebank.account.AccountStatus;
import com.tracebank.transaction.dto.DepositRequest;
import com.tracebank.transaction.dto.TransactionResponse;
import com.tracebank.transaction.dto.WithdrawalRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountService accountService;

    @Transactional
    public TransactionResponse deposit(Long accountId, DepositRequest request) {
        Account account = accountService.findEntityById(accountId);

        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(accountId);
        }

        account.setBalance(account.getBalance().add(request.amount()));

        Transaction transaction = Transaction.builder()
                .type(TransactionType.DEPOSIT)
                .amount(request.amount())
                .timestamp(LocalDateTime.now())
                .destinationAccount(account)
                .build();

        Transaction saved = transactionRepository.save(transaction);
        return transactionMapper.toResponse(saved);
    }

    @Transactional
public TransactionResponse withdraw(Long accountId, WithdrawalRequest request) {
    Account account = accountService.findEntityById(accountId);

    if (account.getStatus() != AccountStatus.ACTIVE) {
        throw new AccountNotActiveException(accountId);
    }

    if (account.getBalance().compareTo(request.amount()) < 0) {
        throw new InsufficientBalanceException(accountId);
    }

    account.setBalance(account.getBalance().subtract(request.amount()));

    Transaction transaction = Transaction.builder()
            .type(TransactionType.WITHDRAWAL)
            .amount(request.amount())
            .timestamp(LocalDateTime.now())
            .sourceAccount(account)
            .build();

    Transaction saved = transactionRepository.save(transaction);
    return transactionMapper.toResponse(saved);
    }
}