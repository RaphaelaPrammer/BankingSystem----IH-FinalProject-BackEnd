package com.ironhack.bankingsystemapp.models.accounts;

import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CheckingAccount extends Account {
    @NotNull
    private String secretKey;

    private final BigDecimal MINIMUM_BALANCE = BigDecimal.valueOf(250);

    private final BigDecimal MONTHLY_MAINTENANCE_FEE = BigDecimal.valueOf(12);

    private LocalDate lastMonthlyMaintenanceFeeApplied = LocalDate.now();
    private LocalDate lastPenaltyFeeApplied = LocalDate.now();
    public CheckingAccount() {
    }

    public CheckingAccount(BigDecimal balance, AccountHolder primaryOwner, String secretKey) {
        super(balance, primaryOwner);
        setSecretKey(secretKey);
    }

    public CheckingAccount(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        setSecretKey(secretKey);
    }
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getMINIMUM_BALANCE() {
        return MINIMUM_BALANCE;
    }

    public BigDecimal getMONTHLY_MAINTENANCE_FEE() {
        return MONTHLY_MAINTENANCE_FEE;
    }

    public LocalDate getLastMonthlyMaintenanceFeeApplied() {
        return lastMonthlyMaintenanceFeeApplied;
    }

    public void setLastMonthlyMaintenanceFeeApplied(LocalDate lastMonthlyMaintenanceFeeApplied) {
        this.lastMonthlyMaintenanceFeeApplied = lastMonthlyMaintenanceFeeApplied;
    }

    public LocalDate getLastPenaltyFeeApplied() {
        return lastPenaltyFeeApplied;
    }

    public void setLastPenaltyFeeApplied(LocalDate lastPenaltyFeeApplied) {
        this.lastPenaltyFeeApplied = lastPenaltyFeeApplied;
    }

    //-----------------------
    // PENALTY FEE - check if actual balance is greater than minimum Balance - if not (condition will be -1), the penalty fee will be deducted.
    public void applyPenaltyFeeChecking(){
        if(super.getBalance().compareTo(MINIMUM_BALANCE)<0){
            if(Period.between(lastPenaltyFeeApplied,LocalDate.now()).getMonths()>3){
                super.setBalance(super.getBalance().subtract(getPENALTY_FEE()));
                setLastPenaltyFeeApplied(LocalDate.now());
            }
        }
    }


// MONTHLY MAINTENANCE FEE - check if current Date is 1month + the date of last maintenance fee applied - add the maintenance fee to the balance and reset this date to +1month
    public void applyMaintenanceFeeChecking(){

        if(Period.between(lastMonthlyMaintenanceFeeApplied, LocalDate.now()).getMonths()>1){
            super.setBalance(super.getBalance().subtract(getMONTHLY_MAINTENANCE_FEE()));
           setLastMonthlyMaintenanceFeeApplied(LocalDate.now());
        }
    }


}
