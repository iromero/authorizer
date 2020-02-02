package com.nubank.model;

import java.time.LocalDateTime;

public class Transfer implements OperationInfo {
    private final String accountId;
    private final String source;
    private final int amount;
    private final LocalDateTime time;

    public Transfer(String accountId, String source, int amount, LocalDateTime time) {
        this.accountId = accountId;
        this.source = source;
        this.amount = amount;
        this.time = time;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getSource() {
        return source;
    }

    public int getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
