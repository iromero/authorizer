package com.nubank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountCreationServiceTest {

    @Test
    public void createSuccessfulAccount() {
        Account accountInfo = new Account(true, 100);
        Account currentAccount = null;
        Account accountResult = new AccountCreationService(currentAccount).createAccount(accountInfo);
        Account accountExpected = new Account(true, 100);
        assertEquals(accountExpected, accountResult);
    }

    @Test
    public void accountAlreadyExist() {
        Account accountInfo = new Account(true, 100);
        Account currentAccount = new Account(true, 100);
        Account accountResult = new AccountCreationService(currentAccount).createAccount(accountInfo);
        Account accountExpected = Account.accountWithViolations(accountInfo);
        assertEquals(accountExpected, accountResult);
    }
}
