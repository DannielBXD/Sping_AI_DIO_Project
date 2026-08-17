package com.br.infrastructure.persistence.repository;

import com.br.domain.Category;
import com.br.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;
import java.util.List;

public interface TransactionEntityRepository extends CrudRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findAllByCategory(Category category);
}
