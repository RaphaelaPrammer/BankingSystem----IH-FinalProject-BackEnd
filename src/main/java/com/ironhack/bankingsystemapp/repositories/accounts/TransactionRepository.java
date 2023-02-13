package com.ironhack.bankingsystemapp.repositories.accounts;

import com.ironhack.bankingsystemapp.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
