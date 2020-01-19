package com.nubank;

public abstract class BankOperation {
    abstract Violations process(Bank bank, BankOperationService service);
}
