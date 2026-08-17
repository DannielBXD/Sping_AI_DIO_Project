package com.br.application.input;

import com.br.domain.Category;
import org.springframework.ai.tool.annotation.ToolParam;

public record PersistTransactionInput(@ToolParam(description = "descrição do gasto") String description,
                                      @ToolParam(description = "Valor do gasto (em centavos)") long amount,
                                      @ToolParam(description = "Categoria de uma transação") Category category) {
}
