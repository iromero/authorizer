package com.nubank.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * A representation for a bank account with a state and a available limit.
 */
public final class Account implements OperationInfo {
    private final String accountId;
    @SerializedName("active-card")
    private final boolean activeCard;
    @SerializedName("available-limit")
    private final int availableLimit;


    public Account(String id, boolean activeCard, int availableLimit) {
        this.accountId = id;
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
    Account debt(Transaction transactionToBeApproved) {
        return new Account(accountId, activeCard, availableLimit - transactionToBeApproved.getAmount());
    }

    public Account add(Transfer transfer) {
        return new Account(accountId, activeCard, availableLimit + transfer.getAmount());
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
                Objects.equals(accountId, account.accountId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activeCard, availableLimit, accountId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId='" + accountId + '\'' +
                ", activeCard=" + activeCard +
                ", availableLimit=" + availableLimit +
                '}';
    }

    @Override
    public String getAccountId() {
        return accountId;
    }


}