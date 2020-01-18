package com.nubank;

public class AccountCreationService {

    private Account currentAccount;

    private AccountCreationService() {
    }

    public AccountCreationService(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public Account createAccount(Account accountInfo) {
        if (currentAccount == null) {
            return Account.newAccount(accountInfo);
        } else {
            return currentAccount.accountAlreadyInitialized();
        }
    }

}