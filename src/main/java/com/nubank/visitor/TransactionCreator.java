package com.nubank.visitor;

import com.nubank.*;

public class TransactionCreator implements TransactionVisitor {

    private final Bank bank;
    private Account currentState;

    public TransactionCreator(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void visit(Account account) {
        currentState = new AccountCreationService(bank.getCurrentAccount()).createAccount(account);

    }

    @Override
    public void visit(Transaction transactionToBeApproved) {
        currentState = new TransactionAuthorizationService(bank.getCurrentAccount(), bank.getApprovedTransactions(),
                transactionToBeApproved).evalTransaction();

    }
}