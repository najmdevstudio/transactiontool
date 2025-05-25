package com.knightriders.transactiontool.services;

import com.knightriders.transactiontool.dto.TransactionDTO;
import com.knightriders.transactiontool.entities.Account;
import com.knightriders.transactiontool.entities.Transaction;
import com.knightriders.transactiontool.repositories.TransactionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class TransactionServiceImpl implements TransactionService{

    private final TransactionRepository transactionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }



    /**
     * @param transactionDTO
     * @return
     */
    @Override
    public Transaction performTransaction(TransactionDTO transactionDTO) {

        Account from = entityManager.find(Account.class, transactionDTO.getFromAccountId());
        Account to = entityManager.find(Account.class, transactionDTO.getToAccountId());

        if (from == null || to == null) {
            throw new IllegalArgumentException("Invalid account id");
        }

        if(from.getId().equals(to.getId())) {
            throw new IllegalArgumentException("Cannot transfer money between same account");
        }

        if(from.getBalance() < transactionDTO.getAmount()) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        from.setBalance(from.getBalance() - transactionDTO.getAmount());
        to.setBalance(to.getBalance() + transactionDTO.getAmount());

        entityManager.persist(from);
        entityManager.persist(to);

        Transaction tx = Transaction.builder()
                .transactionId(UUID.randomUUID().toString())
                .fromAccount(from)
                .toAccount(to)
                .amount(transactionDTO.getAmount())
                .status("SUCCESS")
                .build();

        return transactionRepository.save(tx);
    }

    /**
     * @return
     */
    @Override
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }
}
