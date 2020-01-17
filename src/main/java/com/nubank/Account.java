package com.nubank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Account {

    private final boolean activeCard;
    private final int availableLimit;
    private final List<String> violations;

    public Account(boolean activeCard, int availableLimit) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
        this.violations = new ArrayList<>();
    }


    private Account(Account accountInfo, List<String> violations) {
        this.activeCard = accountInfo.activeCard;
        this.availableLimit = accountInfo.availableLimit;
        this.violations = violations;
    }

    private Account(Account accountInfo) {
        this.activeCard = accountInfo.activeCard;
        this.availableLimit = accountInfo.availableLimit;
        this.violations = new ArrayList<>();
    }

    public Account(boolean activeCard, int availableLimit, List<String> violations) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
        this.violations = violations;
    }

    public static Account newAccount(Account accountInfo) {
        return new Account(accountInfo);
    }

    public static Account accountWithViolations(Account accountInfo) {
        List<String> violations = Arrays.asList("account-already-initialized");
        return new Account(accountInfo, violations);
    }

    public static Account accountNotInitialized() {
        List<String> violations = Arrays.asList("account-not-initialized");
        return new Account(false, 0, violations);
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

    public Account debt(Transaction transactionToBeAproved) {
        return new Account(activeCard, availableLimit - transactionToBeAproved.getAmount());
    }
}