package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

/**
 * Business rule that validates if there is an account initialized before to make a transaction.
 */
public class AccountNotInitializedTransactionRule implements TransactionBusinessRule {
    /**
     * Verify if there is account initialized before allow a transaction
     *
     * @param bank                    The bank with the account info.
     * @param transactionToBeApproved The transaction to be approved.
     * @return If there is not an initialized account it returns a violation otherwise returns no violations.
     */
    @Override
    public Violations evalOperation(Bank bank, Transaction transactionToBeApproved) {
        if (!bank.existAccount(transactionToBeApproved.getAccountId())) {
            return Violations.accountNotInitialized();
        }
        return Violations.noViolations();
    }
}
