package com.nubank;

import com.nubank.json.BankOperationJsonBuilderFactory;
import com.nubank.model.Account;
import com.nubank.model.Violations;

public class ProcessOutputOperation {

    private final Account currentAccount;
    private final Violations violations;

    public ProcessOutputOperation(Account currentAccount, Violations violations) {
        this.currentAccount = currentAccount;
        this.violations = violations;
    }

    public String process() {
        AccountOperation accountStatusWithoutViolations = new AccountOperation(currentAccount, violations);
        String operationOutputResultJson = new BankOperationJsonBuilderFactory().toJson(accountStatusWithoutViolations);
        return operationOutputResultJson;
    }
}
