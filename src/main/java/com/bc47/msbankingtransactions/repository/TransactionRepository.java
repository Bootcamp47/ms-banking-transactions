package com.bc47.msbankingtransactions.repository;

import com.bc47.msbankingtransactions.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, Long> {
}
