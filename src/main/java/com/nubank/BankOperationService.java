package com.nubank;

public interface BankOperationService {
    Account processOperation(Bank bank, Account account);

    Account processOperation(Bank bank, Transaction transaction);
}
