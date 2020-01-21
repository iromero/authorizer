package com.nubank;

import com.google.gson.Gson;
import com.nubank.model.Account;
import com.nubank.model.Bank;
import com.nubank.model.OperationInfo;
import com.nubank.model.Violations;
import com.nubank.service.BankOperationService;
import io.vavr.collection.List;

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

    @Override
    public Violations process(Bank bank, BankOperationService service) {
        return service.processOperation(bank, this);
    }

    @Override
    public OperationInfo getOperationInfo() {
        return account;
    }
}