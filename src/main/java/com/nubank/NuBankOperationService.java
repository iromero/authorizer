package com.nubank;

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