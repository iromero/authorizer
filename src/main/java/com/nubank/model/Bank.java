package com.nubank.model;

import io.vavr.collection.List;

public class Bank {
    private final Account currentAccount;
    private final List<Transaction> approvedTransactions;

    public Bank(Account currentAccount, List<Transaction> approvedTransactions) {
        this.currentAccount = currentAccount;
        this.approvedTransactions = approvedTransactions;
    }

    public static Bank init() {
        return new Bank(null, List.empty());
    }

    public boolean existAccount() {
        return (currentAccount != null);
    }

    public boolean noExistAccount() {
        return (currentAccount == null);
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public List<Transaction> getApprovedTransactions() {
        return approvedTransactions;
    }

    public Bank update(OperationInfo operationInfo) {
        if (currentAccount == null) {
            return initializeAccount((Account) operationInfo);
        }
        return updateAccountAndApprovedTransactions((Transaction) operationInfo);
    }

    public Bank initializeAccount(Account account) {
        return new Bank(account, List.empty());
    }

    public Bank updateAccountAndApprovedTransactions(Transaction transaction) {
        Account accountWithLimitUpdated = currentAccount.debt(transaction);
        List<Transaction> transactionsApproved = approvedTransactions.append(transaction);
        return new Bank(accountWithLimitUpdated, transactionsApproved);
    }
}