package com.ironhack.bankingsystemapp.repositories.accounts;

import com.ironhack.bankingsystemapp.models.accounts.StudentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  StudentAccountRepository extends JpaRepository<StudentAccount,Long> {
}
