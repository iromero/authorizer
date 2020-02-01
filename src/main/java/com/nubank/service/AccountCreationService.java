package com.nubank.service;

import com.nubank.businessrule.AccountAlreadyInitializedRule;
import com.nubank.businessrule.AccountBusinessRule;
import com.nubank.model.Account;
import com.nubank.model.Bank;
import com.nubank.model.Violations;
import io.vavr.collection.List;

/**
 * Service that use a list of business rules to validate an account operation.
 */
public class AccountCreationService implements OperationService {

    private final Bank bank;
    private final Account account;
    private final List<AccountBusinessRule> businessRules;

    AccountCreationService(Bank bank, Account newAccount) {
        this.bank = bank;
        this.account = newAccount;
        this.businessRules = buildBusinessRules();
    }

    /**
     * Build the business rules list that a account operation need to approve.
     *
     * @return The list of business rules that are going to evaluate an account operation.
     */
    private List<AccountBusinessRule> buildBusinessRules() {
        return List.of(
                new AccountAlreadyInitializedRule()
        );
    }

    /**
     * Verify an account operation taking into account the business rules.
     *
     * @return If the account operation does not approve all the business rules it returns violations
     * otherwise it returns an empty violations.
     */
    @Override
    public Violations evalOperation() {
        Violations violations = new Violations();
        for (AccountBusinessRule accountBusinessRule : businessRules) {
            violations = violations.append(accountBusinessRule.evalOperation(bank, account));
        }
        return violations;
    }
}