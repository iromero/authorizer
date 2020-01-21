package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Violations;

public class AccountAlreadyInitializedRule implements AccountBusinessRule {

    @Override
    public Violations evalOperation(Bank bank) {
        if (bank.existAccount()) {
            return Violations.accountAlreadyInitialized();
        }
        return Violations.noViolations();
    }
}
