package com.nubank;

import java.time.Duration;
import java.util.List;

public class TransactionAuthorizationService {

    private final Account currentAcccount;
    private final List<Transaction> transactionsApproved;
    private final Transaction transactionToBeApproved;

    public TransactionAuthorizationService(Account currentAcccount, List<Transaction> transactionsApproved,
                                           Transaction transactionToBeAproved) {
        this.currentAcccount = currentAcccount;
        this.transactionsApproved = transactionsApproved;
        this.transactionToBeApproved = transactionToBeAproved;
    }

    public Account evalTransaction() {
        if (currentAcccount == null) {
            return Account.accountNotInitialized();
        }
        if (currentAcccount.isNotActive()) {
            return currentAcccount.accountWithCardNotActive();
        }
        if (currentAcccount.isNotThereSufficientLimit(transactionToBeApproved.getAmount())) {
            return currentAcccount.accountWithInsuficientLimits();
        }
        if (doesItViolatesDoubleTransaction()) {
            return currentAcccount.accountWithDoubleTransaction();
        }
        if (doesItViolatesHighFrecuencySmallInterval()) {
            return currentAcccount.accountWithHighFrequencySmallInterval();
        }
        return currentAcccount.debt(transactionToBeApproved);
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

    private boolean doesItViolatesHighFrecuencySmallInterval() {
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
