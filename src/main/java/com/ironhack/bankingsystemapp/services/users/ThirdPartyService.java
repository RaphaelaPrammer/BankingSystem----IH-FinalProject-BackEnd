package com.ironhack.bankingsystemapp.services.users;

import com.ironhack.bankingsystemapp.models.users.Role;
import com.ironhack.bankingsystemapp.models.users.ThirdParty;
import com.ironhack.bankingsystemapp.models.users.User;
import com.ironhack.bankingsystemapp.repositories.users.RoleRepository;
import com.ironhack.bankingsystemapp.repositories.users.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ThirdPartyService {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // create a Third Party User with encoded Password, save it to the DB, and add the Role "THIRD-PARTY" to it.
    public ThirdParty addThirdParty (ThirdParty thirdParty){
        thirdParty.setPassword(passwordEncoder.encode(thirdParty.getPassword()));
        ThirdParty newThirdParty = thirdPartyRepository.save(thirdParty);
        roleRepository.save(new Role("THIRD-PARTY",newThirdParty));
        return newThirdParty;
    }

    // delete the Third Party from the Repository
    public void removeThirdParty(Long id){
        thirdPartyRepository.deleteById(id);
    }
}
