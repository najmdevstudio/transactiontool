package com.knightriders.transactiontool.repositories;

import com.knightriders.transactiontool.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
