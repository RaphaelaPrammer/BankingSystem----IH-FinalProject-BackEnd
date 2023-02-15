package com.ironhack.bankingsystemapp.models.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.bankingsystemapp.models.Transaction;
import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(0)
    private BigDecimal balance;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "primary_owner_id")
    private AccountHolder primaryOwner;
    @ManyToOne
    @Nullable
    @JoinColumn(name = "secondary_owner_id")
    private AccountHolder secondaryOwner;

    private final BigDecimal PENALTY_FEE = BigDecimal.valueOf(40);

    private LocalDate creationDate = LocalDate.now() ;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @JsonIgnore
    @OneToMany(mappedBy = "senderAccount", fetch = FetchType.EAGER)
    private List<Transaction> listSenders = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "receiverAccount", fetch = FetchType.EAGER)
    private List<Transaction> listReceivers = new ArrayList<>();

    public Account() {
        this.status = Status.ACTIVE;
        creationDate = LocalDate.now();
    }

    public Account(BigDecimal balance, AccountHolder primaryOwner) {
        setBalance(balance);
        setPrimaryOwner(primaryOwner);
        this.status = Status.ACTIVE;
        creationDate = LocalDate.now();
    }

    public Account(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        setBalance(balance);
        setPrimaryOwner(primaryOwner);
        setSecondaryOwner(secondaryOwner);
        this.status = Status.ACTIVE;
        creationDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal getPENALTY_FEE() {
        return PENALTY_FEE;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Transaction> getListSenders() {
        return listSenders;
    }

    public void setListSenders(List<Transaction> listSenders) {
        this.listSenders = listSenders;
    }

    public List<Transaction> getListReceivers() {
        return listReceivers;
    }

    public void setListReceivers(List<Transaction> listReceivers) {
        this.listReceivers = listReceivers;
    }
}
