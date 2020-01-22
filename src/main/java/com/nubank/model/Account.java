package com.nubank.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * A representation for a bank account with a state and a available limit.
 */
public final class Account implements OperationInfo {
    @SerializedName("active-card")
    private final boolean activeCard;
    @SerializedName("available-limit")
    private final int availableLimit;

    public Account(boolean activeCard, int availableLimit) {
        this.activeCard = activeCard;
        this.availableLimit = availableLimit;
    }

    public boolean isActiveCard() {
        return activeCard;
    }

    public int getAvailableLimit() {
        return availableLimit;
    }

    /**
     * Debt for account a amount specified in a transaction.
     *
     * @param transactionToBeApproved The transaction with the amount to debt.
     * @return a new instance of the account with the new available limit.
     */
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
                availableLimit == account.availableLimit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(activeCard, availableLimit);
    }

    @Override
    public String toString() {
        return "Account{" +
                "activeCard=" + activeCard +
                ", availableLimit=" + availableLimit +
                '}';
    }
}