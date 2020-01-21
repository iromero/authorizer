package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

import java.time.Duration;

public class HighFrequencySmallIntervalTransactionRuleTransaction implements TransactionBusinessRule {

    private static final int HIGH_FREQUENCY_SMALL_INTERVAL = 3;
    private static final int MAX_TRANSACTION_INTERVAL_TIME = 120;

    @Override
    public Violations evalOperation(Bank bank, Transaction transactionToBeApproved) {
        if (doesItViolatesHighFrequencySmallInterval(bank, transactionToBeApproved)) {
            return Violations.accountWithHighFrequencySmallInterval();
        }
        return Violations.noViolations();
    }

    private boolean doesItViolatesHighFrequencySmallInterval(Bank bank, Transaction transactionToBeApproved) {

        NumberOfTransactionsCounter numberOfTransactionsInLessThanTwoMinutes = new NumberOfTransactionsCounter(0);

        for (Transaction transactionApproved : bank.getApprovedTransactions()) {
            Duration duration = Duration.between(transactionApproved.getTime(), transactionToBeApproved.getTime()).abs();
            if (duration.getSeconds() <= MAX_TRANSACTION_INTERVAL_TIME) {
                numberOfTransactionsInLessThanTwoMinutes = numberOfTransactionsInLessThanTwoMinutes.increment();
            }
        }
        // Number of already transactions approved in less than two minutes taking as reference the incoming transaction.
        return numberOfTransactionsInLessThanTwoMinutes.isCounterBiggerEqualThan(HIGH_FREQUENCY_SMALL_INTERVAL);
    }
}