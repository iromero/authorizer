package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

/**
 * Interface that should implements all the transaction business rules.
 */
public interface TransactionBusinessRule {
    Violations evalOperation(Bank bank, Transaction transactionToBeApproved);
}
