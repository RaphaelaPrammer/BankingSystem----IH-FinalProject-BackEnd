package com.ironhack.bankingsystemapp.models;

import com.ironhack.bankingsystemapp.models.accounts.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The Sender Account cannot be empty")
    @ManyToOne
    @JoinColumn(name="sender_account_id")
    private Account senderAccount;

    @NotNull(message = "The Receiver Account cannot be empty")
    @ManyToOne
    @JoinColumn(name="receiver_account_id")
    private Account receiverAccount;

    @NotNull
    private String receiverName;

    @NotNull
    private BigDecimal transferAmount;

    private LocalDateTime transactionDate;

    public Transaction() {
    }

    public Transaction(Account senderAccount, Account receiverAccount, String receiverName, BigDecimal transferAmount) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.receiverName = receiverName;
        this.transferAmount = transferAmount;
        this.transactionDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
