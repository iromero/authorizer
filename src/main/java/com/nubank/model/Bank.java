package com.nubank.model;

import io.vavr.collection.List;

/**
 * A representation of bank containing an account a the approved transactions.
 */
public class Bank {
    private final Account currentAccount;
    private final List<Transaction> approvedTransactions;

    public Bank(Account currentAccount, List<Transaction> approvedTransactions) {
        this.currentAccount = currentAccount;
        this.approvedTransactions = approvedTransactions;
    }

    /**
     * Initialized the bank.
     *
     * @return instance of the bank without a account and an empty list of approved transactions.
     */
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

    /**
     * Apply an bank operation.
     *
     * @param operationInfo The bank operation info. That is a account initialization or
     *                      a transaction.
     * @return a new bank instance with the account and approved transactions updated.
     */
    public Bank update(OperationInfo operationInfo) {
        if (currentAccount == null) {
            return initializeAccount((Account) operationInfo);
        }
        return updateAccountAndApprovedTransactions((Transaction) operationInfo);
    }

    /**
     * Initialize the account in the bank.
     *
     * @param account The account info
     * @return a new bank instance with the account initialize.
     */
    public Bank initializeAccount(Account account) {
        return new Bank(account, List.empty());
    }

    /**
     * Add to the bank a new approved transaction and at same time create a new account instance
     * with a new available limit.
     *
     * @param transaction
     * @return a new instance of the bank that contains a new instances of the account with a new available limit
     * and a new instance of approved transactions that contains the new one.
     */
    public Bank updateAccountAndApprovedTransactions(Transaction transaction) {
        Account accountWithLimitUpdated = currentAccount.debt(transaction);
        List<Transaction> transactionsApproved = approvedTransactions.append(transaction);
        return new Bank(accountWithLimitUpdated, transactionsApproved);
    }
}