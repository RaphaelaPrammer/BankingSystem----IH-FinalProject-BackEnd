package com.ironhack.bankingsystemapp.repositories.users;

import com.ironhack.bankingsystemapp.models.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}