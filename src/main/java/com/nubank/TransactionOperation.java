package com.nubank;

public class TransactionOperation extends BankOperation {

    private final Transaction transaction;

    public TransactionOperation(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    Violations process(Bank bank, BankOperationService service) {
        return service.processOperation(bank, this);
    }

    @Override
    OperationInfo getOperationInfo() {
        return transaction;
    }
}