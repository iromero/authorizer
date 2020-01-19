package com.nubank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountCreationServiceTest {

    @Test
    public void noViolations() {
        //given
        Account accountInfo = new Account(true, 100);
        Account currentAccount = null;

        //when
        Violations violations = new AccountCreationService(currentAccount).createAccount(accountInfo);

        //then
        Account accountExpected = new Account(true, 100);
        assertEquals(Violations.noViolations(), violations);
    }

    @Test
    public void accountAlreadyExist() {
        //given
        Account accountInfo = new Account(true, 350);
        Account currentAccount = new Account(true, 100);

        //when
        Violations violations = new AccountCreationService(currentAccount).createAccount(accountInfo);

        //then
        assertEquals(Violations.accountAlreadyInitialized(), violations);
    }
}
