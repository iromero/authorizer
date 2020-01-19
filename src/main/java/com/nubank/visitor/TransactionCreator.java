package com.nubank.visitor;

import com.nubank.*;

public class TransactionCreator implements TransactionVisitor {

    private final Bank bank;

    public TransactionCreator(Bank bank) {
        this.bank = bank;
    }

    @Override
    public Account visit(Account account) {
        return new AccountCreationService(bank.getCurrentAccount()).createAccount(account);
    }

    @Override
    public Account visit(Transaction transactionToBeApproved) {
        return new TransactionAuthorizationService(bank.getCurrentAccount(), bank.getApprovedTransactions(),
                transactionToBeApproved).evalTransaction();
    }
}