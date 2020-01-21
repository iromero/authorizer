package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

public class InsufficientLimitsTransactionRule implements BusinessRule {
    @Override
    public Violations evalOperation(Bank bank, Transaction transactionToBeApproved) {
        if (bank.existAccount() && bank.getCurrentAccount().isNotThereSufficientLimit(transactionToBeApproved.getAmount())) {
            return Violations.accountWithInsufficientLimits();
        }
        return Violations.noViolations();
    }
}
