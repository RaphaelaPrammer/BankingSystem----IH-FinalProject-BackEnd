package com.ironhack.bankingsystemapp.repositories.users;

import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountHolderRepository extends JpaRepository <AccountHolder, Long>{
    Optional<AccountHolder> findByUsername(String userName);
}
