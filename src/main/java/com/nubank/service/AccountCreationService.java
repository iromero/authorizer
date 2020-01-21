package com.nubank.service;

import com.nubank.businessrule.AccountAlreadyInitializedRule;
import com.nubank.businessrule.AccountBusinessRule;
import com.nubank.model.Bank;
import com.nubank.model.Violations;
import io.vavr.collection.List;

public class AccountCreationService implements OperationService {

    private final Bank bank;
    private final List<AccountBusinessRule> businessRules;

    public AccountCreationService(Bank bank) {
        this.bank = bank;
        this.businessRules = buildBusinessRules();
    }

    public List<AccountBusinessRule> buildBusinessRules() {
        List<AccountBusinessRule> businessRuleList = List.of(
                new AccountAlreadyInitializedRule()
        );
        return businessRuleList;
    }

    @Override
    public Violations evalOperation() {
        Violations violations = new Violations();
        for (AccountBusinessRule accountBusinessRule : businessRules) {
            violations = violations.append(accountBusinessRule.evalOperation(bank));
        }
        return violations;
    }
}