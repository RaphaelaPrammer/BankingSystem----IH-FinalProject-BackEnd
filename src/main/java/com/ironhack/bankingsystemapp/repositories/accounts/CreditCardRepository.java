package com.ironhack.bankingsystemapp.repositories.accounts;

import com.ironhack.bankingsystemapp.models.accounts.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard,Long> {
}
