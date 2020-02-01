package com.nubank.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A representation for a transaction info.
 */
public class Transaction implements OperationInfo {

    private final String accountId;
    private final String merchant;
    private final int amount;
    private final LocalDateTime time;


    public Transaction(String accountId, String merchant, int amount, LocalDateTime time) {
        this.accountId = accountId;
        this.merchant = merchant;
        this.amount = amount;
        this.time = time;
    }

    public String getAccountId() {
        return accountId;
    }

    public int getAmount() {
        return amount;
    }

    public String getMerchant() {
        return merchant;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public boolean sameAmountAndMerchant(Transaction transaction) {
        return (amount == transaction.getAmount() && merchant.equals(transaction.getMerchant()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return amount == that.amount &&
                Objects.equals(merchant, that.merchant) &&
                Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchant, amount, time);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "merchant='" + merchant + '\'' +
                ", amount=" + amount +
                ", time=" + time +
                '}';
    }
}