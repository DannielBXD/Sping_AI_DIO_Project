package com.br.application.output;

import com.br.domain.Category;
import com.br.domain.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record TransactionOutput(String id,
                                String description,
                                String Category,
                                double value) {
    public static TransactionOutput from(Transaction transaction) {
        return new TransactionOutput(transaction.getId().uuid().toString(),
                transaction.getDescription(),
                transaction.getCategory().name(),
                BigDecimal.valueOf(transaction.getAmount()).setScale(2, RoundingMode.HALF_UP).doubleValue());
    }
}
