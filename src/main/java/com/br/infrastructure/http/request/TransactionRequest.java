package com.br.infrastructure.http.request;

import com.br.application.input.PersistTransactionInput;
import com.br.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionRequest(@NotBlank String description,
                                 @NotNull Category category,
                                 @Positive long amount) {
    public PersistTransactionInput toInput() {
        return new PersistTransactionInput(description, amount, category);
    }
}
