package com.nubank;

import com.google.gson.Gson;
import com.nubank.model.Account;
import com.nubank.model.Bank;
import com.nubank.model.OperationInfo;
import com.nubank.model.Violations;
import com.nubank.service.BankOperationService;
import io.vavr.collection.List;

/**
 * It contains the account info as well as a list of possible violation to represent
 * the state of a bank operation.
 */
public class AccountOperation implements BankOperation {
    private final Account account;
    private final List<String> violations;

    public AccountOperation(Account account) {
        this.account = account;
        this.violations = Violations.noViolations().getViolations();
    }

    public AccountOperation(Account currentAccount, Violations violations) {
        this.account = currentAccount;
        this.violations = violations.getViolations();
    }

    /**
     * Process the account operation.
     *
     * @param bank    The bank that contains the info.
     * @param service A service that implements a double dispatcher so the service knows that it is going to
     *                process a account operation.
     * @return The violations of processing the account operation.
     */
    @Override
    public Violations process(Bank bank, BankOperationService service) {
        return service.processOperation(bank, this);
    }

    @Override
    public OperationInfo getOperationInfo() {
        return account;
    }
}