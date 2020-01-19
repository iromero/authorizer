package com.nubank;

import io.vavr.collection.List;

public class AccountOperation extends BankOperation {
    private final Account account;
    private final List<String> violations;

    public AccountOperation(Account account) {
        this.account = account;
        violations = List.empty();
    }

    @Override
    Violations process(Bank bank, BankOperationService service) {
        return service.processOperation(bank, this);
    }

    @Override
    OperationInfo getOperationInfo() {
        return account;
    }
}