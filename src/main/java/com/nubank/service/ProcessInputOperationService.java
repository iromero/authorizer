package com.nubank.service;

import com.nubank.operation.BankOperation;
import com.nubank.json.BankOperationJsonBuilderFactory;
import com.nubank.model.Bank;
import com.nubank.model.Violations;

/**
 * Service to process operations from input stream.
 */
public class ProcessInputOperationService {

    private final Bank bank;
    private final String bankOperationJson;

    public ProcessInputOperationService(Bank bank, String bankOperationJson) {
        this.bank = bank;
        this.bankOperationJson = bankOperationJson;
    }

    /**
     * Process an json input stream bank operation, it could be an account or transaction operation.
     * The process include validate the bank operation.
     *
     * @return The result of the process for the bank operation. The process could contains violation
     * and the bank operation itself.
     */
    public ProcessInputOperationResult process() {
        BankOperation bankOperation = new BankOperationJsonBuilderFactory().fromJson(bankOperationJson);
        BankOperationService service = new NuBankOperationService();
        Violations violations = bankOperation.process(bank, service);
        return new ProcessInputOperationResult(violations, bankOperation.getOperationInfo());
    }
}
