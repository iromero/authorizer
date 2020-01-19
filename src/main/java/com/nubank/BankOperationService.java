package com.nubank;

public interface BankOperationService {
    Violations processOperation(Bank bank, AccountOperation account);

    Violations processOperation(Bank bank, TransactionOperation transaction);
}
