package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

public interface BusinessRule {
    public Violations evalOperation(Bank bank, Transaction transactionToBeApproved);
}
