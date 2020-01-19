package com.nubank;

import java.time.LocalDateTime;
import java.util.Objects;


public class Transaction extends OperationInfo {

    public String merchant;
    public int amount;
    public LocalDateTime time;

    public Transaction() {
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Transaction(String merchant, int amount, LocalDateTime time) {
        this.merchant = merchant;
        this.amount = amount;
        this.time = time;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return amount == that.amount &&
                Objects.equals(merchant, that.merchant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchant, amount);
    }
}