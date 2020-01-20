package com.nubank.service;

import com.nubank.AccountOperation;
import com.nubank.model.Bank;
import com.nubank.TransactionOperation;
import com.nubank.model.Violations;

public class NuBankOperationService implements BankOperationService {
    @Override
    public Violations processOperation(Bank bank, AccountOperation account) {
        return null;
    }

    @Override
    public Violations processOperation(Bank bank, TransactionOperation transaction) {
        return null;
    }
}