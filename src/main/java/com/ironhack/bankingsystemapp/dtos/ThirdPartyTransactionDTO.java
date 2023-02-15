package com.ironhack.bankingsystemapp.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ThirdPartyTransactionDTO {

    private Long thirdPartyId;
    private BigDecimal amount;
    private Long accountId;
    private String secretKey;
    private LocalDateTime transactionDate = LocalDateTime.now();

    public ThirdPartyTransactionDTO() {
    }

    public ThirdPartyTransactionDTO(Long thirdPartyId, BigDecimal amount, Long accountId, String secretKey, LocalDateTime transactionDate) {
        this.thirdPartyId = thirdPartyId;
        this.amount = amount;
        this.accountId = accountId;
        this.secretKey = secretKey;
        this.transactionDate = transactionDate;
    }

    public Long getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(Long thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
