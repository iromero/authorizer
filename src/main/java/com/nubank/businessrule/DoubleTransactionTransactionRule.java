package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

import java.time.Duration;

/**
 * Business rule that validates if there are more than 1 similar transaction in a 2 minutes interval.
 */
public class DoubleTransactionTransactionRule implements TransactionBusinessRule {
    private static final int MAX_TRANSACTION_INTERVAL_TIME = 120;

    /**
     * Verify if there are more than 1 similar transaction in a 2 minutes interval.
     *
     * @param bank                    The bank with the existing approved transactions
     * @param transactionToBeApproved The transaction that it needs to validate.
     * @return If there is an approved transaction similar to the one that needs to be approved it returns a violation
     * otherwise returns no violations.
     */
    @Override
    public Violations evalOperation(Bank bank, Transaction transactionToBeApproved) {
        if (doesItViolatesDoubleTransaction(bank, transactionToBeApproved)) {
            return Violations.accountWithDoubleTransaction();
        }
        return Violations.noViolations();
    }

    private boolean doesItViolatesDoubleTransaction(Bank bank, Transaction transactionToBeApproved) {
        for (Transaction transactionApproved : bank.getApprovedTransactions()) {
            Duration duration = Duration.between(transactionApproved.getTime(), transactionToBeApproved.getTime()).abs();
            if (transactionApproved.sameAmountAndMerchant(transactionToBeApproved) && duration.getSeconds() <= MAX_TRANSACTION_INTERVAL_TIME) {
                return true;
            }
        }
        return false;
    }
}
