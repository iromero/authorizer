package com.nubank;

import com.nubank.json.BankOperationJsonBuilderFactory;
import com.nubank.model.Account;
import com.nubank.model.Violations;

/**
 * Service to process the output of a bank operation. The output is a json representation with possible violations.
 */
public class ProcessOutputOperation {

    private final Account currentAccount;
    private final Violations violations;

    public ProcessOutputOperation(Account currentAccount, Violations violations) {
        this.currentAccount = currentAccount;
        this.violations = violations;
    }

    /**
     * Takes the output of a bank operation and build a expected json with possible violations.
     *
     * @return The json representation of the state of the account after the bank operation as well as
     * the list of possible violations.
     */
    public String process() {
        AccountOperation accountStatusWithoutViolations = new AccountOperation(currentAccount, violations);
        String operationOutputResultJson = new BankOperationJsonBuilderFactory().toJson(accountStatusWithoutViolations);
        return operationOutputResultJson;
    }
}
