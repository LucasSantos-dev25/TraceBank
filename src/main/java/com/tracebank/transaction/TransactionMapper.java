package com.tracebank.transaction;

import com.tracebank.transaction.dto.TransactionResponse;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionResponse toResponse(Transaction transaction) {
        Long sourceId = transaction.getSourceAccount() != null
                ? transaction.getSourceAccount().getId()
                : null;
        Long destinationId = transaction.getDestinationAccount() != null
                ? transaction.getDestinationAccount().getId()
                : null;

        return new TransactionResponse(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getTimestamp(),
                sourceId,
                destinationId
        );
    }
}