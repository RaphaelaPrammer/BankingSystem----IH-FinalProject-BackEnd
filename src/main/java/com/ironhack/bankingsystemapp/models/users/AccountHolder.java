package com.ironhack.bankingsystemapp.models.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.bankingsystemapp.models.accounts.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AccountHolder extends User {
    @NotNull
    private LocalDate dateOfBirth;

    @Embedded
    @NotNull
    private Address primaryAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "addressName", column = @Column(name = "mail_address_name")),
            @AttributeOverride(name = "city", column = @Column(name = "mail_city")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "mail_postal_code")),
            @AttributeOverride(name = "country", column = @Column(name = "mail_country"))
    })
    private Address mailingAddress;
    @OneToMany(mappedBy = "primaryOwner", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Account> primaryAccounts = new ArrayList<>();
    @OneToMany(mappedBy = "secondaryOwner", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Account> secondaryAccounts = new ArrayList<>();


    public AccountHolder() {
    }

    public AccountHolder(String name, String username, String password, LocalDate dateOfBirth, Address primaryAddress) {
        super(name, username, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
    }

    public AccountHolder(String name, String username, String password, LocalDate dateOfBirth, Address primaryAddress, Address mailingAddress) {
        super(name, username, password);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public List<Account> getPrimaryAccounts() {
        return primaryAccounts;
    }

    public void setPrimaryAccounts(List<Account> primaryAccounts) {
        this.primaryAccounts = primaryAccounts;
    }

    public List<Account> getSecondaryAccounts() {
        return secondaryAccounts;
    }

    public void setSecondaryAccounts(List<Account> secondaryAccounts) {
        this.secondaryAccounts = secondaryAccounts;
    }
}
