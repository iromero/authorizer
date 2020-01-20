package com.nubank.service;

import com.nubank.AccountOperation;
import com.nubank.TransactionOperation;
import com.nubank.model.Account;
import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;

public class NuBankOperationService implements BankOperationService {
    @Override
    public Violations processOperation(Bank bank, AccountOperation accountOperation) {
        return new AccountCreationService(bank.getCurrentAccount()).createAccount((Account) accountOperation.getOperationInfo());
    }

    @Override
    public Violations processOperation(Bank bank, TransactionOperation transactionOperation) {
        return new TransactionAuthorizationService(bank.getCurrentAccount(), bank.getApprovedTransactions(),
                (Transaction) transactionOperation.getOperationInfo()).evalTransaction();
    }
}