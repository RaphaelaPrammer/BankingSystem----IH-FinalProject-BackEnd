package com.ironhack.bankingsystemapp.models.users;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User{

//    private String name;
    public Admin() {
    }

    public Admin(String name, String username, String password) {
        super(name, username, password);

    }


}
