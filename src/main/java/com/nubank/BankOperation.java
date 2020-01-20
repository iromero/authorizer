package com.nubank;

import com.nubank.model.Bank;
import com.nubank.model.OperationInfo;
import com.nubank.model.Violations;
import com.nubank.service.BankOperationService;

public abstract class BankOperation {
    abstract Violations process(Bank bank, BankOperationService service);
    abstract OperationInfo getOperationInfo();
}
