package com.nubank.service;

import com.nubank.operation.AccountOperation;
import com.nubank.model.Bank;
import com.nubank.operation.TransactionOperation;
import com.nubank.model.Violations;

/**
 * Interface for all the bank operations.
 */
public interface BankOperationService {
    Violations processOperation(Bank bank, AccountOperation account);

    Violations processOperation(Bank bank, TransactionOperation transaction);
}
