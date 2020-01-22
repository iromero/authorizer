package com.nubank.service;

import com.nubank.operation.AccountOperation;
import com.nubank.operation.TransactionOperation;
import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

/**
 * Service that call the specific service to process a Bank operation.
 */
public class NuBankOperationService implements BankOperationService {
    @Override
    public Violations processOperation(Bank bank, AccountOperation accountOperation) {
        return new AccountCreationService(bank).evalOperation();
    }

    @Override
    public Violations processOperation(Bank bank, TransactionOperation transactionOperation) {
        return new TransactionAuthorizationService(bank,
                (Transaction) transactionOperation.getOperationInfo()).evalOperation();
    }
}