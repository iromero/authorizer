package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

public class AccountNotInitializedRuleTransaction implements TransactionBusinessRule {
    @Override
    public Violations evalOperation(Bank bank, Transaction transactionToBeApproved) {
        if (!bank.existAccount()) {
            return Violations.accountNotInitialized();
        }
        return Violations.noViolations();
    }
}
