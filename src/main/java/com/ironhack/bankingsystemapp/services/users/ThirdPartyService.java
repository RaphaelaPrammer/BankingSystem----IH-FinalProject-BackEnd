package com.ironhack.bankingsystemapp.services.users;

import com.ironhack.bankingsystemapp.models.users.Admin;
import com.ironhack.bankingsystemapp.models.users.Role;
import com.ironhack.bankingsystemapp.models.users.ThirdParty;
import com.ironhack.bankingsystemapp.models.users.User;
import com.ironhack.bankingsystemapp.repositories.users.RoleRepository;
import com.ironhack.bankingsystemapp.repositories.users.ThirdPartyRepository;
import com.ironhack.bankingsystemapp.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // create a Third Party User with encoded Password, save it to the DB, and add the Role "THIRD-PARTY" to it.
    public ThirdParty addThirdParty (ThirdParty thirdParty){
        thirdParty.setPassword(passwordEncoder.encode(thirdParty.getPassword()));
        ThirdParty newThirdParty = userRepository.save(thirdParty);
        Role role = roleRepository.findByRole("THIRD-PARTY");
        newThirdParty.getRoles().add(role);
        userRepository.save(newThirdParty);

        return newThirdParty;
    }

    // delete the Third Party from the Repository
    public void removeThirdParty(Long id){
        thirdPartyRepository.deleteById(id);
    }
}
