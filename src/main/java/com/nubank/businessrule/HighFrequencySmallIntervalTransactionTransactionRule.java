package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

import java.time.Duration;

/**
 * Business rule that validates there are not more than 3 transactions on a 2 minutes interval.
 */
public class HighFrequencySmallIntervalTransactionTransactionRule implements TransactionBusinessRule {

    private static final int HIGH_FREQUENCY_SMALL_INTERVAL = 3;
    private static final int MAX_TRANSACTION_INTERVAL_TIME = 120;

    /**
     * Verify it there are more than 3 transactions on a 2 minutes interval.
     *
     * @param bank                    Bank with the exiting approved transactions.
     * @param transactionToBeApproved The transaction that it needs to validate.
     * @return if there are more than 3 transactions on a 2 minutes interval returns a violation
     * otherwise returns no violations.
     */
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