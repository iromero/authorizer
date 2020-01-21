package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

public interface TransactionBusinessRule {
    Violations evalOperation(Bank bank, Transaction transactionToBeApproved);
}
