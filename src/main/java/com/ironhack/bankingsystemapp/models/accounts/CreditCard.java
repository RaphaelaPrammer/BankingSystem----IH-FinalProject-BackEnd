package com.ironhack.bankingsystemapp.models.accounts;

import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCard extends  Account{
    @DecimalMax("100.00")
    @DecimalMax("100000.00")
    @NotNull
    private BigDecimal creditLimit = BigDecimal.valueOf(100);

    @DecimalMin("0.1")
    @DecimalMax("0.2")
    @Digits(integer=0, fraction=4)
    private BigDecimal interestRate =BigDecimal.valueOf(0.2);

    private LocalDate lastInterestRateApplied = LocalDate.now();


    public CreditCard() {
    }
    public CreditCard(BigDecimal balance, AccountHolder primaryOwner) {
        super(balance, primaryOwner);
    }
    public CreditCard(BigDecimal balance, AccountHolder primaryOwner, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public CreditCard(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
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
