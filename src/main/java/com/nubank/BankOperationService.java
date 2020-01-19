package com.nubank;

public interface BankOperationService {
    Violations processOperation(Bank bank, Account account);

    Violations processOperation(Bank bank, Transaction transaction);
}
