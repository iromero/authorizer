package com.nubank.operation;

import com.nubank.model.Bank;
import com.nubank.model.OperationInfo;
import com.nubank.model.Transfer;
import com.nubank.model.Violations;
import com.nubank.service.BankOperationService;

public class TransferOperation implements BankOperation {

    private final Transfer transfer;

    public TransferOperation(Transfer transfer) {
        this.transfer = transfer;
    }

    @Override
    public Violations process(Bank bank, BankOperationService service) {
        return service.processOperation(bank, this);
    }

    @Override
    public OperationInfo getOperationInfo() {
        return transfer;
    }
}
