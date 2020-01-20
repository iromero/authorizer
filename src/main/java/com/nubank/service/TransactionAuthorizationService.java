package com.nubank.service;

import com.nubank.model.Account;
import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

import java.time.Duration;

public class TransactionAuthorizationService implements OperationService {

    private static final int TWO_MINUTES = 120;
    private static final int HIGH_FREQUENCY_SMALL_INTERVAL = 3;
    private final Bank bank;
    private final Transaction transactionToBeApproved;

    public TransactionAuthorizationService(Bank bank, Transaction transactionToBeApproved) {
        this.bank = bank;
        this.transactionToBeApproved = transactionToBeApproved;
    }

    @Override
    public Violations evalOperation() {
        if (!bank.existAccount()) {
            return Violations.accountNotInitialized();
        }

        Account currentAccount = bank.getCurrentAccount();

        if (currentAccount.isNotActive()) {
            return Violations.accountWithCardNotActive();
        }
        if (currentAccount.isNotThereSufficientLimit(transactionToBeApproved.getAmount())) {
            return Violations.accountWithInsufficientLimits();
        }
        if (doesItViolatesDoubleTransaction()) {
            return Violations.accountWithDoubleTransaction();
        }
        if (doesItViolatesHighFrequencySmallInterval()) {
            return Violations.accountWithHighFrequencySmallInterval();
        }
        return Violations.noViolations();
    }

    public boolean doesItViolatesDoubleTransaction() {
        for (Transaction transactionApproved : bank.getApprovedTransactions()) {
            Duration duration = Duration.between(transactionApproved.getTime(), transactionToBeApproved.getTime()).abs();
            if (transactionApproved.sameAmountAndMerchant(transactionToBeApproved) && duration.getSeconds() <= TWO_MINUTES) {
                return true;
            }
        }
        return false;
    }

    private boolean doesItViolatesHighFrequencySmallInterval() {
        int numberOfTransactionsInLessThanTwoMinutes = 0;
        for (Transaction transactionApproved : bank.getApprovedTransactions()) {
            Duration duration = Duration.between(transactionApproved.getTime(), transactionToBeApproved.getTime()).abs();
            if (duration.getSeconds() <= TWO_MINUTES) {
                numberOfTransactionsInLessThanTwoMinutes++;
            }
        }
        // Number of already transactions approved in less than two minutes taking as reference the incoming transaction.
        return (numberOfTransactionsInLessThanTwoMinutes >= HIGH_FREQUENCY_SMALL_INTERVAL);
    }
}