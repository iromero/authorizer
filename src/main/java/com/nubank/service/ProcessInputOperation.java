package com.nubank.service;

import com.nubank.BankOperation;
import com.nubank.json.BankOperationJsonBuilderFactory;
import com.nubank.model.Bank;
import com.nubank.model.Violations;

public class ProcessInputOperation {

    private final Bank bank;
    private final String bankOperationJson;

    public ProcessInputOperation(Bank bank, String bankOperationJson) {
        this.bank = bank;
        this.bankOperationJson = bankOperationJson;
    }

    public ProcessInputOperationResult process() {
        BankOperation bankOperation = new BankOperationJsonBuilderFactory().fromJson(bankOperationJson);
        BankOperationService service = new NuBankOperationService();
        Violations violations = bankOperation.process(bank, service);
        return new ProcessInputOperationResult(violations, bankOperation.getOperationInfo());
    }
}
