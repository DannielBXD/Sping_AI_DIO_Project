package com.br.infrastructure.http.response;

import com.br.application.output.TransactionOutput;


public record TransactionResponse(String id, String description, String category, double amount) {
    public static TransactionResponse from(TransactionOutput output) {
        return new TransactionResponse(output.id(), output.description(), output.Category(), output.value());
    }
}
