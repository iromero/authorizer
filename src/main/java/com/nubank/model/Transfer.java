package com.nubank.model;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public Bank updateInBank(Bank bank) {
        return bank.update(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return amount == transfer.amount &&
                Objects.equals(accountId, transfer.accountId) &&
                Objects.equals(source, transfer.source) &&
                Objects.equals(time, transfer.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, source, amount, time);
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "accountId='" + accountId + '\'' +
                ", source='" + source + '\'' +
                ", amount=" + amount +
                ", time=" + time +
                '}';
    }
}