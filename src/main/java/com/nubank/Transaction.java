package com.nubank;

import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction extends BankOperation {

    private final String merchant;
    private final int amount;
    private final LocalDateTime time;

    public Transaction(String merchant, int amount, LocalDateTime time) {
        this.merchant = merchant;
        this.amount = amount;
        this.time = time;
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

    @Override
    public Violations process(Bank bank, BankOperationService service) {
        return service.processOperation(bank, this);
    }

//    @Override
//    public void accept(TransactionVisitor visitor) {
//        visitor.visit(this);
//    }
//
//    public Account processOperation() {
//        return null;
//    }
}