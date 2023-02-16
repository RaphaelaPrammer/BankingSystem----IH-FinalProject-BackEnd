package com.ironhack.bankingsystemapp.models.accounts;

import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.Period;

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
    private LocalDate lastPenaltyFeeApplied = LocalDate.now();

    public SavingsAccount() {
    }

    public SavingsAccount(BigDecimal balance, AccountHolder primaryOwner, String secretKey) {
        super(balance, primaryOwner);
        setSecretKey(secretKey);
    }

    public SavingsAccount(BigDecimal balance, AccountHolder primaryOwner, String secretKey, BigDecimal minimumBalance, BigDecimal interestRate) {
        super(balance, primaryOwner);
        setSecretKey(secretKey);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
    }

    public SavingsAccount(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey, BigDecimal minimumBalance, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
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
        // Setting Values for minimum Balance, that when they are created they respect the min/max values (min = 100, max = 1000)
        if(minimumBalance.compareTo(BigDecimal.valueOf(100))<0){
            this.minimumBalance=new BigDecimal("100");
        } else if(minimumBalance.compareTo(BigDecimal.valueOf(1000))>0){
            this.minimumBalance=new BigDecimal("1000");
        }else{
            this.minimumBalance = minimumBalance;
        }
    }

    public BigDecimal getInterestRate() {

        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        // Setting Values for interest Rate, that when a Savings Account is created they respect the min/max values (min = 0.0025, max = 0.5)
        if(interestRate.compareTo(BigDecimal.valueOf(0.5))>0){
            this.interestRate = new BigDecimal("0.5");
        }else if(interestRate.compareTo(BigDecimal.valueOf(0.0025))<0) {
            this.interestRate=new BigDecimal("0.0025");
        } else{
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

    // PENALTY FEE - check if actual balance is greater than minimum Balance - if not (condition will be -1), the penalty fee will be deducted.
    // this condition will be applied only after 3 months after the last application of the penalty fee
    public void applyPenaltyFeeSavings(){
        if(super.getBalance().compareTo(minimumBalance)<0){
            if(Period.between(lastPenaltyFeeApplied,LocalDate.now()).getMonths()>3){
                super.setBalance(super.getBalance().subtract(getPENALTY_FEE()));
                setLastPenaltyFeeApplied(LocalDate.now());
            }
        }
    }


// INTEREST RATE
    public void applyInterestRateSavings(){
        // check if last Time the interest rate has been applied is more than one year, we add the interest rate to the balance.
        if(Period.between(lastInterestRateApplied,LocalDate.now()).getYears()>1){
            super.setBalance(super.getBalance().add(super.getBalance().multiply(interestRate)));
            // reset the lastInterestRateApplied Date
            setLastInterestRateApplied(LocalDate.now());
        }
    }






}
