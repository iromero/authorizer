package com.nubank;

public interface BankOperation {
    Account process(Bank bank, BankOperationService service);
}
