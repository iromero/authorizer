package com.nubank.service;

import com.nubank.model.Account;
import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountCreationServiceTest {

    @Test
    public void testNoViolations() {
        //given
        Bank bank = Bank.init();//There is not account
        Account account = new Account("1", true, 200);

        //when
        Violations violations = new AccountCreationService(bank, account).evalOperation();

        //then
        assertEquals(Violations.noViolations(), violations);
    }

    @Test
    public void testNoViolationExistingOtherAccounts() {
        //give
        Bank bank = new Bank(getCurrentAccounts(), getEmptyTransactionsForCurrentAccounts());
        Account newAccount = new Account("2", true, 300);

        //when
        Violations violations = new AccountCreationService(bank, newAccount).evalOperation();

        //then
        assertEquals(Violations.noViolations(), violations);
    }

    @Test
    public void testAccountAlreadyExist() {
        //given
        Bank bank = new Bank(getCurrentAccounts(), getEmptyTransactionsForCurrentAccounts());
        Account account = new Account("1", true, 200);

        //when
        Violations violations = new AccountCreationService(bank, account).evalOperation();

        //then
        assertEquals(Violations.accountAlreadyInitialized(), violations);
    }

    private Map<String, Account> getCurrentAccounts() {
        return HashMap.ofEntries(Map.entry("1", new Account("1", true, 100)));
    }

    private Map<String, List<Transaction>> getEmptyTransactionsForCurrentAccounts() {
        return HashMap.ofEntries(
                Map.entry("1", List.empty())
        );
    }
}