package com.nubank;

import com.nubank.model.Bank;
import com.nubank.model.OperationInfo;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;
import com.nubank.service.BankOperationService;

public class TransactionOperation implements BankOperation {

    private final Transaction transaction;

    public TransactionOperation(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public Violations process(Bank bank, BankOperationService service) {
        return service.processOperation(bank, this);
    }

    @Override
    public OperationInfo getOperationInfo() {
        return transaction;
    }
}