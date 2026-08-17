package com.br.application;

import com.br.application.input.PersistTransactionInput;
import com.br.application.output.TransactionOutput;
import com.br.domain.Category;
import com.br.domain.Transaction;
import com.br.domain.TransactionRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PersistTransactionUseCase {
    private final TransactionRepository transactionRepository;

    public PersistTransactionUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Tool(name = "persist-transaction", description = "Persiste uma nova transação financeira")
    public TransactionOutput execute(PersistTransactionInput input) {
        var transaction = transactionRepository.save(
                new Transaction(
                        input.description(),
                        input.amount(),
                        input.category()
                )
        );
        return TransactionOutput.from(transaction);
    }

}
