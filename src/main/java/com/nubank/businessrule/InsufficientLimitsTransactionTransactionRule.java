package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

/**
 * Business rule that validates if there is enough money in the account to make the transaction.
 */
public class InsufficientLimitsTransactionTransactionRule implements TransactionBusinessRule {
    /**
     * Verify if there is enough money to debt in the account for the transaction.
     *
     * @param bank                    The bank with the account information.
     * @param transactionToBeApproved The transaction that it needs to verify.
     * @return If there is not enough money to debt in the account it returns a violation otherwise returns no violations.
     */
    @Override
    public Violations evalOperation(Bank bank, Transaction transactionToBeApproved) {
        if (bank.existAccount(transactionToBeApproved.getAccountId())
                && bank.getCurrentAccount(transactionToBeApproved.getAccountId()).get()
                .isNotThereSufficientLimit(transactionToBeApproved.getAmount())) {
            return Violations.accountWithInsufficientLimits();
        }
        return Violations.noViolations();
    }
}
