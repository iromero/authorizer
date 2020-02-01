package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

/**
 * Business rule that validates if there is an active account before approve a transaction.
 */
public class AccountNoActiveTransactionRule implements TransactionBusinessRule {
    /**
     * Verified if there is an active account before allow a transaction
     *
     * @param bank                    Bank with the account info
     * @param transactionToBeApproved The transaction that is going to be approved.
     * @return If there is not an active account it returns a "account with card not active" violation other return
     * empty violations.
     */
    @Override
    public Violations evalOperation(Bank bank, Transaction transactionToBeApproved) {
        if (bank.existAccount(transactionToBeApproved.getAccountId())
                && bank.getCurrentAccount(transactionToBeApproved.getAccountId()).get().isNotActive()) {
            return Violations.accountWithCardNotActive();
        }
        return Violations.noViolations();
    }
}