package com.knightriders.transactiontool.services;

import com.knightriders.transactiontool.dto.TransactionDTO;
import com.knightriders.transactiontool.entities.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction performTransaction(TransactionDTO transactionDTO);
    List<Transaction> getTransactions();
}
