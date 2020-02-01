package com.nubank.model;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;

/**
 * A representation of bank containing an account a the approved transactions.
 */
public class Bank {
    private final Map<String, Account> currentAccounts;
    private final List<Transaction> approvedTransactions;

    public Bank(Map<String, Account> currentAccounts, List<Transaction> approvedTransactions) {
        this.currentAccounts = currentAccounts;
        this.approvedTransactions = approvedTransactions;
    }

    /**
     * Initialized the bank.
     *
     * @return instance of the bank without a account and an empty list of approved transactions.
     */
    public static Bank init() {
        return new Bank(HashMap.empty(), List.empty());
    }

    public boolean existAccount(String accountId) {
        return currentAccounts.containsKey(accountId);
    }

    public Option<Account> getCurrentAccount(String accountId) {
        return currentAccounts.get(accountId);
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

        if (!existAccount(operationInfo.getAccountId())) {
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
    private Bank initializeAccount(Account account) {
        return new Bank(currentAccounts.put(account.getAccountId(), account), List.empty());
    }

    /**
     * Add to the bank a new approved transaction and at same time create a new account instance
     * with a new available limit.
     *
     * @param transaction Transaction that contains the amount that will be used for the account limit update.
     * @return a new instance of the bank that contains a new instances of the account with a new available limit
     * and a new instance of approved transactions that contains the new one.
     */
    Bank updateAccountAndApprovedTransactions(Transaction transaction) {
        Option<Account> currentAccountOption = currentAccounts.get(transaction.getAccountId());
        Account currentAccount = currentAccountOption.get();
        Account accountWithLimitUpdated = currentAccount.debt(transaction);
        Map<String, Account> newCurrentAccounts = currentAccounts.put(transaction.getAccountId(), accountWithLimitUpdated);
        List<Transaction> transactionsApproved = approvedTransactions.append(transaction);
        return new Bank(newCurrentAccounts, transactionsApproved);
    }
}