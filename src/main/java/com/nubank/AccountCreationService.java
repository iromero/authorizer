package com.nubank;

public class AccountCreationService {

    private Account currentAccount;

    public AccountCreationService(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public Violations createAccount(Account accountInfo) {
        if (currentAccount == null) {
            return Violations.noViolations();
        } else {
            return Violations.accountAlreadyInitialized();
        }
    }
}