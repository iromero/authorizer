package com.nubank;

import io.vavr.collection.List;

public class Bank {
    private final Account currentAccount;
    private final List<Transaction> approvedTransactions;

    private Bank(Account currentAccount, List<Transaction> approvedTransactions) {
        this.currentAccount = currentAccount;
        this.approvedTransactions = approvedTransactions;
    }

    public static Bank init() {
        return new Bank(null, List.empty());
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public List<Transaction> getApprovedTransactions() {
        return approvedTransactions;
    }

    public Bank update(BankOperation bankOperation) {
        if (currentAccount == null) {
            return initializeAccount((Account) bankOperation);
        }
        return updateAccountAndApprovedTransactions((Transaction) bankOperation);
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