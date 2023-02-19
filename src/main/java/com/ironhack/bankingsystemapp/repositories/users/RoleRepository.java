package com.ironhack.bankingsystemapp.repositories.users;

import com.ironhack.bankingsystemapp.models.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

   Role findByRole(String role);

   //Role findByUserId(Long id);
}
