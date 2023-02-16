package com.ironhack.bankingsystemapp.repositories.accounts;

import com.ironhack.bankingsystemapp.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> findByReceiverAccountId(Long receiverId);
    public List<Transaction> findBySenderAccountId(Long senderId);

    //public List<Transaction> findBySenderAccountIdOrReceiverAccountId(Long id);
}
