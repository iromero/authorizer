package com.nubank;

import io.vavr.collection.List;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction {

    private final String merchant;
    private final int amount;
    private final LocalDateTime time;
    private final List<String> violations;

    public Transaction(String merchant, int amount, LocalDateTime time) {
        this.merchant = merchant;
        this.amount = amount;
        this.time = time;
        this.violations = List.empty();
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
        Transaction that = (Transaction) o;
        return amount == that.amount &&
                Objects.equals(merchant, that.merchant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merchant, amount);
    }
}