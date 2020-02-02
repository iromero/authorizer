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
    private final Map<String, List<Transaction>> approvedTransactions;
    private final Map<String, List<Transfer>> approvedtransfers;

    public Bank(Map<String, Account> currentAccounts, Map<String, List<Transaction>> approvedTransactions) {
        this.currentAccounts = currentAccounts;
        this.approvedTransactions = approvedTransactions;
        this.approvedtransfers = HashMap.empty();
    }

    public Bank(Map<String, Account> currentAccounts, Map<String, List<Transaction>> approvedTransactions,
                Map<String, List<Transfer>> approvedTransfers) {
        this.currentAccounts = currentAccounts;
        this.approvedTransactions = approvedTransactions;
        this.approvedtransfers = approvedTransfers;
    }

    /**
     * Initialized the bank.
     *
     * @return instance of the bank without a account and an empty list of approved transactions.
     */
    public static Bank init() {
        return new Bank(HashMap.empty(), HashMap.empty(), HashMap.empty());
    }

    public boolean existAccount(String accountId) {
        return currentAccounts.containsKey(accountId);
    }

    public Option<Account> getCurrentAccount(String accountId) {
        return currentAccounts.get(accountId);
    }

    public Option<List<Transaction>> getApprovedTransactions(String accountId) {
        return approvedTransactions.get(accountId);
    }

    /**
     * Gets the all the transfers for a given account.
     *
     * @param accountId The accountId that owns the transfer .
     * @return The list of transfer for the specific account.
     */
    public Option<List<Transfer>> getApprovedTransfers(String accountId) {
        return approvedtransfers.get(accountId);
    }

    public Bank update(Account account) {
        return initializeAccount(account);
    }

    public Bank update(Transaction transaction) {
        return updateAccountAndApprovedTransactions(transaction);
    }

    public Bank update(Transfer transfer) {
        return updateAccountAndApprovedTransfer(transfer);
    }

    /**
     * Initialize the account in the bank.
     *
     * @param account The account info
     * @return a new bank instance with the account initialize.
     */
    private Bank initializeAccount(Account account) {
        return new Bank(
                currentAccounts.put(account.getAccountId(), account),
                approvedTransactions.put(account.getAccountId(), List.empty()),
                approvedtransfers.put(account.getAccountId(), List.empty())
        );
    }

    /**
     * Add to the bank a new approved transaction and at same time create a new account instance
     * with a new available limit.
     *
     * @param transaction Transaction that contains the amount that will be used for the account limit update.
     * @return a new instance of the bank that contains a new instances of the account with a new available limit
     * and a new instance of approved transactions that contains the new one.
     */
    private Bank updateAccountAndApprovedTransactions(Transaction transaction) {
        Option<Account> currentAccountOption = currentAccounts.get(transaction.getAccountId());
        Account currentAccount = currentAccountOption.get();
        Account accountWithLimitUpdated = currentAccount.debt(transaction);
        Map<String, Account> newCurrentAccounts = currentAccounts.put(transaction.getAccountId(), accountWithLimitUpdated);
        List<Transaction> transactionsApproved = approvedTransactions.get(transaction.getAccountId()).get().append(transaction);
        Map<String, List<Transaction>> newTransactionsApproved = approvedTransactions.put(transaction.getAccountId(), transactionsApproved);
        return new Bank(newCurrentAccounts, newTransactionsApproved, approvedtransfers);
    }

    private Bank updateAccountAndApprovedTransfer(Transfer transfer) {
        Option<Account> currentAccountOption = currentAccounts.get(transfer.getAccountId());
        Account currentAccount = currentAccountOption.get();
        Account accountWithLimitUpdated = currentAccount.add(transfer);
        Map<String, Account> newCurrentAccounts = currentAccounts.put(transfer.getAccountId(), accountWithLimitUpdated);
        List<Transfer> transfersApproved = approvedtransfers.get(transfer.getAccountId()).get().append(transfer);
        Map<String, List<Transfer>> newTransfersApproved = approvedtransfers.put(transfer.getAccountId(), transfersApproved);
        return new Bank(newCurrentAccounts, approvedTransactions, newTransfersApproved);
    }

}