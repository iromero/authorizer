package com.nubank;

import com.nubank.model.Bank;
import com.nubank.model.OperationInfo;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;
import com.nubank.service.BankOperationService;

/**
 * Contains the transaction info as well the knowledge of how to process the transaction.
 */
public class TransactionOperation implements BankOperation {

    private final Transaction transaction;

    public TransactionOperation(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * Process the transaction operation.
     *
     * @param bank    The bank that contains the info.
     * @param service A service that implements a double dispatcher so the service knows that it is going to
     *                process a transaction operation.
     * @return The violations of processing the transaction operation.
     */
    @Override
    public Violations process(Bank bank, BankOperationService service) {
        return service.processOperation(bank, this);
    }


    @Override
    public OperationInfo getOperationInfo() {
        return transaction;
    }
}