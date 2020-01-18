package com.nubank;

import io.vavr.collection.List;

import java.util.Objects;

public final class Account {

    private final boolean activeCard;
    private final int availableLimit;
    private List<String> violations;

    public Account(boolean activeCard, int availableLimit) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
        this.violations = List.empty();
    }

    private Account(Account accountInfo, List<String> violations) {
        this.activeCard = accountInfo.activeCard;
        this.availableLimit = accountInfo.availableLimit;
        this.violations = violations;
    }

    private Account(Account accountInfo) {
        this.activeCard = accountInfo.activeCard;
        this.availableLimit = accountInfo.availableLimit;
        this.violations = List.empty();
    }

    public Account(boolean activeCard, int availableLimit, List<String> violations) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
        this.violations = violations;
    }

    public static Account newAccount(Account accountInfo) {
        return new Account(accountInfo);
    }

    public Account accountAlreadyInitialized() {
        List<String> violations = List.of("account-already-initialized");
        return new Account(this, violations);
    }

    public static Account accountNotInitialized() {
        List<String> violations = List.of("account-not-initialized");
        return new Account(false, 0, violations);
    }

    public Account accountWithCardNotActive() {
        List<String> violations = List.of("card-not-active");
        return new Account(this, violations);
    }

    public Account accountWithInsufficientLimits() {
        List<String> violations = List.of("insufficient-limit");
        return new Account(this, violations);
    }

    public Account accountWithHighFrequencySmallInterval() {
        List<String> violations = List.of("high-frequency-small-interval");
        return new Account(this, violations);
    }


    public Account accountWithDoubleTransaction() {
        List<String> violations = List.of("double-transaction");
        return new Account(this, violations);
    }

    public Account debt(Transaction transactionToBeAproved) {
        return new Account(activeCard, availableLimit - transactionToBeAproved.getAmount());
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
}