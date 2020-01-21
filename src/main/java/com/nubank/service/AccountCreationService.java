package com.nubank.service;

import com.nubank.model.Bank;
import com.nubank.model.Violations;

public class AccountCreationService implements OperationService {

    private Bank bank;

    public AccountCreationService(Bank bank) {
        this.bank = bank;
    }

    @Override
    public Violations evalOperation() {
        if (bank.noExistAccount()) {
            return Violations.noViolations();
        } else {
            return Violations.accountAlreadyInitialized();
        }
    }
}