package com.ironhack.bankingsystemapp.models.accounts;

import com.ironhack.bankingsystemapp.models.users.AccountHolder;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CheckingAccount extends Account {
    @NotNull
    private String secretKey;

    private final BigDecimal MINIMUM_BALANCE = BigDecimal.valueOf(250);

    private final BigDecimal MONTHLY_MAINTENANCE_FEE = BigDecimal.valueOf(12);


    public CheckingAccount() {
    }

    public CheckingAccount(BigDecimal balance, AccountHolder primaryOwner, String secretKey) {
        super(balance, primaryOwner);
        setSecretKey(secretKey);
    }

    public CheckingAccount(BigDecimal balance, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
    }

    // check if actual balance is greater than minimum Balance - if not (condition will be -1), the penalty fee will be deducted.
//    @Override
//    public void setBalance(BigDecimal balance) {
//        if(balance.compareTo(MINIMUM_BALANCE)<0){
//            super.setBalance(balance.subtract(getPENALTY_FEE()));
//        }
//        super.setBalance(balance);
//    }

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
}
