package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

import java.time.Duration;

public class DoubleTransactionRule implements BusinessRule {
    private static final int MAX_TRANSACTION_INTERVAL_TIME = 120;

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
