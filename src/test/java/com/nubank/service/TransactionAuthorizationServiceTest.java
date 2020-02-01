package com.nubank.service;

import com.nubank.model.Account;
import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import io.vavr.collection.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionAuthorizationServiceTest {

    @Test
    void testNoViolations() {
        //given
        Account currentAccount = new Account("1", true, 100);
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("1", "Burger King", 20, dateTime);
        Bank bank = new Bank(getCurrentAccounts(), List.empty());

        //when
        Violations violations = new TransactionAuthorizationService(bank, transactionToBeApproved).evalOperation();

        //then
        assertEquals(Violations.noViolations(), violations);
    }

    @Test
    void testAccountNoInitialized() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("1", "Burger King", 20, dateTime);
        Bank bank = Bank.init();//The account is not initialized.

        //when
        Violations violations = new TransactionAuthorizationService(bank, transactionToBeApproved).evalOperation();

        //then
        assertEquals(Violations.accountNotInitialized(), violations);
    }

    @Test
    void testCardNotActive() {
        //given
        Map<String,Account> currentAccounts = HashMap.ofEntries(Map.entry("1", new Account("1", false, 100)));
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("1", "Burger King", 20, dateTime);
        Bank bank = new Bank(currentAccounts, List.empty());

        //when
        Violations violations = new TransactionAuthorizationService(bank, transactionToBeApproved).evalOperation();

        //then
        assertEquals(Violations.accountWithCardNotActive(), violations);
    }

    @Test
    void testInsufficientLimit() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("1", "Burger King", 110, dateTime);
        Bank bank = new Bank(getCurrentAccounts(), List.empty());

        //when
        Violations violations = new TransactionAuthorizationService(bank, transactionToBeApproved).evalOperation();

        //then
        assertEquals(Violations.accountWithInsufficientLimits(), violations);
    }

    @Test
    void testHighFrequencySmallInterval() {
        //given
        List<Transaction> approvedTransactions = getApprovedTransactions();
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 30, 0);
        Transaction transactionToBeApproved = new Transaction("1", "Grills", 20, dateTime);
        Bank bank = new Bank(getCurrentAccounts(), approvedTransactions);

        //when
        Violations violations = new TransactionAuthorizationService(bank, transactionToBeApproved).evalOperation();

        //then
        assertEquals(Violations.accountWithHighFrequencySmallInterval(), violations);
    }

    @Test
    void testDoubledTransaction() {
        //given
        List<Transaction> approvedTransactions = getApprovedTransactions();
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 2, 55, 0);
        Transaction transactionToBeApproved = new Transaction("1", "Mac Donall's", 20, dateTime);
        Bank bank = new Bank(getCurrentAccounts(), approvedTransactions);

        //when
        Violations violations = new TransactionAuthorizationService(bank, transactionToBeApproved).evalOperation();

        //then
        assertEquals(Violations.accountWithDoubleTransaction(), violations);
    }

    private List<Transaction> getApprovedTransactions() {
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionApproved1 = new Transaction("1", "Burger King", 20, dateTime);

        LocalDateTime dateTime2 = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 0, 0);
        Transaction transactionApproved2 = new Transaction("1", "Mac Donall's", 20, dateTime2);

        LocalDateTime dateTime3 = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 55, 0);
        Transaction transactionApproved3 = new Transaction("1", "Presto", 20, dateTime3);

        return List.of(transactionApproved1, transactionApproved2, transactionApproved3);
    }

    private Map<String, Account> getCurrentAccounts() {
        return HashMap.ofEntries(Map.entry("1", new Account("1", true, 100)));
    }
}
