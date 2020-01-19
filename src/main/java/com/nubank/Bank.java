package com.nubank;

import io.vavr.collection.List;

public class Bank {
    private Account currentAccount;
    private List<Transaction> approvedTransactions;

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public List<Transaction> getApprovedTransactions() {
        return approvedTransactions;
    }
}
