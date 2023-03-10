package com.ironhack.bankingsystemapp.services.users;

import com.ironhack.bankingsystemapp.models.users.Admin;
import com.ironhack.bankingsystemapp.models.users.Role;
import com.ironhack.bankingsystemapp.models.users.User;
import com.ironhack.bankingsystemapp.repositories.users.AdminRepository;
import com.ironhack.bankingsystemapp.repositories.users.RoleRepository;
import com.ironhack.bankingsystemapp.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
@Autowired
UserService userService;
@Autowired
    UserRepository userRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    // create Admin User, encode the password, add it to the DB and add the Role "ADMIN" to it.
    public Admin addAdmin(Admin admin){

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Admin newAdmin = adminRepository.save(admin);
        Role role = roleRepository.findByRole("ADMIN");
        // Add the role to the user's role collection
        newAdmin.getRoles().add(role);
        adminRepository.save(newAdmin);
        //roleRepository.save(new Role("ADMIN", newAdmin));

        return newAdmin;
    }

}
