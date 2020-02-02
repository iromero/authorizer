package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

import java.time.Duration;

public class MaxAmountExpendTransactionRule implements TransactionBusinessRule {
    private static final int MAX_TRANSACTION_INTERVAL_TIME = 120;
    private static final int MAX_AMOUNT_ALLOWED_TO_EXPEND = 150;

    @Override
    public Violations evalOperation(Bank bank, Transaction transactionToBeApproved) {
        if (bank.existAccount(transactionToBeApproved.getAccountId()) && doesItViolatesMaxAmount(bank, transactionToBeApproved)) {
            return Violations.maxAmountExpend();
        }
        return Violations.noViolations();
    }

    private boolean doesItViolatesMaxAmount(Bank bank, Transaction transactionToBeApproved) {
        MaxAmountAccumulator accumulator = new MaxAmountAccumulator(transactionToBeApproved.getAmount());
        for (Transaction transaction : bank.getApprovedTransactions(transactionToBeApproved.getAccountId()).get()) {
            Duration duration = Duration.between(transaction.getTime(), transactionToBeApproved.getTime()).abs();
            if (duration.getSeconds() < MAX_TRANSACTION_INTERVAL_TIME) {
                accumulator = accumulator.increment(transaction.getAmount());
            }
        }
        return (accumulator.getAmount() > MAX_AMOUNT_ALLOWED_TO_EXPEND);
    }
}
