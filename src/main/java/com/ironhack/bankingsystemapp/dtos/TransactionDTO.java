package com.ironhack.bankingsystemapp.dtos;

import java.math.BigDecimal;

public class TransactionDTO {

    private Long senderAccountId;
    private Long receiverAccountId;
    private String receiverName;
    private BigDecimal amount;

    public TransactionDTO() {
    }

    public TransactionDTO(Long senderAccountId, Long receiverAccountId, String receiverName, BigDecimal amount) {
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.receiverName = receiverName;
        this.amount = amount;
    }

    public Long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public Long getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(Long receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
