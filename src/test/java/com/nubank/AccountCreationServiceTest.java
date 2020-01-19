package com.nubank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountCreationServiceTest {

    @Test
    public void createSuccessfulAccount() {
        //given
        Account accountInfo = new Account(true, 100);
        Account currentAccount = null;

        //when
        Account accountResult = new AccountCreationService(currentAccount).createAccount(accountInfo);

        //then
        Account accountExpected = new Account(true, 100);
        assertEquals(accountExpected, accountResult);
    }

    @Test
    public void accountAlreadyExist() {
        //given
        Account accountInfo = new Account(true, 350);
        Account currentAccount = new Account(true, 100);

        //when
        Account accountResult = new AccountCreationService(currentAccount).createAccount(accountInfo);

        //then
        Account accountExpected = currentAccount.accountAlreadyInitialized();
        assertEquals(accountExpected, accountResult);
    }
}
