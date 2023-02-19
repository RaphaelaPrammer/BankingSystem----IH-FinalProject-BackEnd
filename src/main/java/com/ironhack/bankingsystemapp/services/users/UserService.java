package com.ironhack.bankingsystemapp.services.users;

import com.ironhack.bankingsystemapp.models.users.User;
import com.ironhack.bankingsystemapp.models.users.Role;
import com.ironhack.bankingsystemapp.repositories.users.RoleRepository;
import com.ironhack.bankingsystemapp.repositories.users.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements  UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve user with the given username
        User user = userRepository.findByUsername(username);
        // Check if user exists
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
            // Create a collection of SimpleGrantedAuthority objects from the user's roles
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRole()));
            });
            // Return the user details, including the username, password, and authorities
           return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }

    // ---- create new Users ---
    // --> functionality within AccountHolderService, AdminService, ThirdPartyService
    // because there was a circular dependency with the passwordEncoder.

    // -------- Add a new Role -------
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getRole());
        return roleRepository.save(role);

    }

    // --------- Add Role to User by Username ---------
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);

        // Retrieve the user and role objects from the repository
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRole(roleName);

        // Add the role to the user's role collection
        user.getRoles().add(role);

        // Save the user to persist the changes
        userRepository.save(user);
    }

   // ------- get User-Info by Username ------
    public User getUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    // get a List of all Users
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    // delete User
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

}
