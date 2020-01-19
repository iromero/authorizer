package com.nubank;

import io.vavr.collection.List;

import java.util.Objects;

public final class Account extends BankOperation {

    private final boolean activeCard;
    private final int availableLimit;
    private final List<String> violations;

    public Account(boolean activeCard, int availableLimit) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
        this.violations = List.empty();
    }

    public Account(boolean activeCard, int availableLimit, List<String> violations) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
        this.violations = violations;
    }

    public Account debt(Transaction transactionToBeApproved) {
        return new Account(activeCard, availableLimit - transactionToBeApproved.getAmount());
    }

    public boolean isNotActive() {
        return !activeCard;
    }

    public boolean isNotThereSufficientLimit(int amount) {
        return availableLimit < amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return activeCard == account.activeCard &&
                availableLimit == account.availableLimit &&
                violations.equals(account.violations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activeCard, availableLimit, violations);
    }

    @Override
    public String toString() {
        return "Account{" +
                "activeCard=" + activeCard +
                ", availableLimit=" + availableLimit +
                ", violations=" + violations +
                '}';
    }

    @Override
    public Violations process(Bank bank, BankOperationService service) {
        return service.processOperation(bank, this);
    }
}