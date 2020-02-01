package com.nubank.service;

import com.nubank.model.Account;
import com.nubank.model.Bank;
import com.nubank.model.Violations;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountCreationServiceTest {

    @Test
    public void testNoViolations() {
        //given
        Bank bank = Bank.init();//There is not account

        //when
        Violations violations = new AccountCreationService(bank).evalOperation();

        //then
        assertEquals(Violations.noViolations(), violations);
    }

    @Test
    public void testAccountAlreadyExist() {
        //given
        Bank bank = new Bank(new Account("1", true, 100), List.empty());

        //when
        Violations violations = new AccountCreationService(bank).evalOperation();

        //then
        assertEquals(Violations.accountAlreadyInitialized(), violations);
    }
}