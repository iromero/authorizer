package com.nubank;

public class NuBankOperationService implements BankOperationService {
    @Override
    public Violations processOperation(Bank bank, Account account) {
        return new AccountCreationService(bank.getCurrentAccount()).createAccount(account);
    }

    @Override
    public Violations processOperation(Bank bank, Transaction transactionToBeApproved) {
        return new TransactionAuthorizationService(bank.getCurrentAccount(), bank.getApprovedTransactions(),
                transactionToBeApproved).evalTransaction();
    }
}
