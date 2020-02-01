package com.nubank.businessrule;

import com.nubank.model.Account;
import com.nubank.model.Bank;
import com.nubank.model.Violations;

/**
 * Business rule that validates if an account was already initialized.
 */
public class AccountAlreadyInitializedRule implements AccountBusinessRule {
    /**
     * Check if an account was previously initialized.
     *
     * @param bank bank with the account info.
     * @return if there is an account already initialized returns a violation for that otherwise returns no violations
     */
    @Override
    public Violations evalOperation(Bank bank, Account account) {
        if (bank.existAccount(account.getAccountId())) {
            return Violations.accountAlreadyInitialized();
        }
        return Violations.noViolations();
    }
}
