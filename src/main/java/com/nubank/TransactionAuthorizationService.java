package com.nubank;

import io.vavr.collection.List;

import java.time.Duration;

public class TransactionAuthorizationService {

    private final Account currentAccount;
    private final List<Transaction> transactionsApproved;
    private final Transaction transactionToBeApproved;

    public TransactionAuthorizationService(Account currentAccount, List<Transaction> transactionsApproved,
                                           Transaction transactionToBeApproved) {
        this.currentAccount = currentAccount;
        this.transactionsApproved = transactionsApproved;
        this.transactionToBeApproved = transactionToBeApproved;
    }

    public Account evalTransaction() {
        if (currentAccount == null) {
            return Account.accountNotInitialized();
        }
        if (currentAccount.isNotActive()) {
            return currentAccount.accountWithCardNotActive();
        }
        if (currentAccount.isNotThereSufficientLimit(transactionToBeApproved.getAmount())) {
            return currentAccount.accountWithInsufficientLimits();
        }
        if (doesItViolatesDoubleTransaction()) {
            return currentAccount.accountWithDoubleTransaction();
        }
        if (doesItViolatesHighFrequencySmallInterval()) {
            return currentAccount.accountWithHighFrequencySmallInterval();
        }
        return currentAccount.debt(transactionToBeApproved);
    }

    public boolean doesItViolatesDoubleTransaction() {
        for (Transaction transactionApproved : transactionsApproved) {
            Duration duration = Duration.between(transactionApproved.getTime(), transactionToBeApproved.getTime()).abs();
            if (transactionApproved.equals(transactionToBeApproved) && duration.getSeconds() <= 120) {
                return true;
            }
        }
        return false;
    }

    private boolean doesItViolatesHighFrequencySmallInterval() {
        int numberOfTransactionsInLessThanTwoMinutes = 0;
        for (Transaction transactionApproved : transactionsApproved) {
            Duration duration = Duration.between(transactionApproved.getTime(), transactionToBeApproved.getTime()).abs();
            if (duration.getSeconds() <= 120) {
                numberOfTransactionsInLessThanTwoMinutes++;
            }
        }
        // Number of already transactions approved in less than two minutes taking as reference the incoming transaction.
        return (numberOfTransactionsInLessThanTwoMinutes >= 3);
    }


}
