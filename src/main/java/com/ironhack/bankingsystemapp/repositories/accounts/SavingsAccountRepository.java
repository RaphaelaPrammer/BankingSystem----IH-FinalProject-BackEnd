package com.ironhack.bankingsystemapp.repositories.accounts;

import com.ironhack.bankingsystemapp.models.accounts.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {

    Optional<SavingsAccount> findByPrimaryOwnerId(Long id);
}
