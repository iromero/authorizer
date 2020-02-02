package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transfer;
import com.nubank.model.Violations;

public class AccountNoInitializedTransferRule implements TransferBusinessRule {
    @Override
    public Violations evalOperation(Bank bank, Transfer transferToBeApproved) {
        if (!bank.existAccount(transferToBeApproved.getAccountId())) {
            return Violations.accountNotInitialized();
        }
        return Violations.noViolations();
    }
}
