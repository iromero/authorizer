package com.nubank.service;

import com.nubank.model.*;
import com.nubank.operation.AccountOperation;
import com.nubank.operation.TransactionOperation;
import com.nubank.operation.TransferOperation;

/**
 * Service that call the specific service to process a Bank operation.
 */
public class NuBankOperationService implements BankOperationService {
    @Override
    public Violations processOperation(Bank bank, AccountOperation accountOperation) {
        return new AccountCreationService(bank, (Account) accountOperation.getOperationInfo()).evalOperation();
    }

    @Override
    public Violations processOperation(Bank bank, TransactionOperation transactionOperation) {
        return new TransactionAuthorizationService(bank,
                (Transaction) transactionOperation.getOperationInfo()).evalOperation();
    }

    @Override
    public Violations processOperation(Bank bank, TransferOperation transferOperation) {
        return new TransferAuthorizationService(bank, (Transfer) transferOperation.getOperationInfo()).evalOperation();
    }
}