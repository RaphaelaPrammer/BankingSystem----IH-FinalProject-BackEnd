package com.ironhack.bankingsystemapp.models.accounts;

import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class SavingsAccount extends Account{
    @NotNull
    private String secretKey;

    @DecimalMin("100.00")
    @DecimalMax("1000.00")
    @NotNull
    private BigDecimal minimumBalance = BigDecimal.valueOf(1000);

    @DecimalMin("0.0025")
    @DecimalMax("0.5")
    @Digits(fraction=4, integer=0)
    private BigDecimal interestRate = new BigDecimal("0.0025", new MathContext(4));

    private LocalDate lastInterestRateApplied = LocalDate.now();

    public SavingsAccount() {
    }

    public SavingsAccount(BigDecimal balance, AccountHolder primaryOwner, String secretKey) {
        super(balance, primaryOwner);
        setSecretKey(secretKey);
    }

    public SavingsAccount(BigDecimal balance, AccountHolder primaryOwner, String secretKey, BigDecimal minimumBalance, BigDecimal interestRate) {
        super(balance, primaryOwner);
        setSecretKey(secretKey);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    public SavingsAccount(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, BigDecimal minimumBalance, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getLastInterestRateApplied() {
        return lastInterestRateApplied;
    }

    public void setLastInterestRateApplied(LocalDate lastInterestRateApplied) {
        this.lastInterestRateApplied = lastInterestRateApplied;
    }
}
