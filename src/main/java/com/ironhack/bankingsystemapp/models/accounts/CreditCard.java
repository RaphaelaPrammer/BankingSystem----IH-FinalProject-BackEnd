package com.ironhack.bankingsystemapp.models.accounts;

import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

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
    private LocalDate lastPenaltyFeeApplied = LocalDate.now();


    public CreditCard() {
    }
    public CreditCard(BigDecimal balance, AccountHolder primaryOwner) {
         super(balance, primaryOwner);

    }
    public CreditCard(BigDecimal balance, AccountHolder primaryOwner, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);

    }

    public CreditCard(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        setCreditLimit(creditLimit);
        setInterestRate(interestRate);

    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        // Setting Values for Credit Limit, that when they Credit Card is created it respects the min/max values (min = 100, max = 100000)
        if(creditLimit.compareTo(BigDecimal.valueOf(100))<0){
            this.creditLimit=new BigDecimal("100");
        } else if(creditLimit.compareTo(BigDecimal.valueOf(100000))>0){
            this.creditLimit = new BigDecimal("100000");
        }else{
            this.creditLimit = creditLimit;
        }
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        // Setting Values for interest Rate for Credit Cards, that when they Credit Card is created it respects the min/max values (min = 0.1, max = 0.2)
        if(interestRate.compareTo(BigDecimal.valueOf(0.1))<0){
            this.interestRate=new BigDecimal("0.1");
        } else if(interestRate.compareTo(BigDecimal.valueOf(0.2))>0){
            this.interestRate = new BigDecimal("0.2");
        }else{
            this.interestRate = interestRate;
        }
    }

    public LocalDate getLastInterestRateApplied() {
        return lastInterestRateApplied;
    }

    public void setLastInterestRateApplied(LocalDate lastInterestRateApplied) {
        this.lastInterestRateApplied = lastInterestRateApplied;
    }

    public LocalDate getLastPenaltyFeeApplied() {
        return lastPenaltyFeeApplied;
    }

    public void setLastPenaltyFeeApplied(LocalDate lastPenaltyFeeApplied) {
        this.lastPenaltyFeeApplied = lastPenaltyFeeApplied;
    }

    // PENALTY FEE - check if actual balance is greater than credit Limit , the penalty fee will be deducted.
    // this condition will be applied only after 3 months after the last application of the penalty fee
    public void applyPenaltyFeeCredit(){
        if(super.getBalance().compareTo(creditLimit)<0){
            if(Period.between(lastPenaltyFeeApplied,LocalDate.now()).getMonths()>2.999){
                super.setBalance(super.getBalance().subtract(getPENALTY_FEE()));
                setLastPenaltyFeeApplied(LocalDate.now());
            }
        }
    }

    // INTEREST RATE
    public void applyInterestRateCredit(){
        // check if last Time the interest rate has been applied is more than one year, we add the interest rate to the balance.
        Period period = Period.between(lastInterestRateApplied,LocalDate.now());
        if(period.getMonths()>0.999){
            super.setBalance(super.getBalance().add(super.getBalance().multiply(interestRate.multiply(BigDecimal.valueOf(period.getMonths())))));
            // reset the lastInterestRateApplied Date, add the months
            setLastInterestRateApplied(lastInterestRateApplied.plusYears(period.getMonths()));
        }
    }

}
