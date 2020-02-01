package com.nubank.model;

import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {

    @Test
    public void testAccountInitialized() {
        //given
        Bank bank = Bank.init();
        Account account = new Account("1", true, 100);

        //when
        Bank bankUpdated = bank.update(account);

        //then
        assertEquals(account, bankUpdated.getCurrentAccount());
    }

    @Test
    public void testAccountAvailableLimitAfterTransaction() {
        //given
        Account account = new Account("1", true, 100);
        Bank bank = new Bank(account, List.empty());
        Transaction transaction = new Transaction("1", "Burger King", 20, LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0));

        //when
        Bank bankUpdated = bank.updateAccountAndApprovedTransactions(transaction);

        //then
        Account accountWithLimitUpdated = bankUpdated.getCurrentAccount();
        assertEquals(new Account("1", true, 80), bankUpdated.getCurrentAccount());
    }

    @Test
    public void testApprovedTransactionsAfterTransaction() {
        //given
        Account account = new Account("1", true, 100);
        List<Transaction> approvedTransactions = getApprovedTransactions();
        Bank bank = new Bank(account, approvedTransactions);
        Transaction transaction = new Transaction("1", "Burger King", 20, LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0));

        //when
        Bank bankUpdated = bank.updateAccountAndApprovedTransactions(transaction);
        List<Transaction> approvedTransactionsUpdated = bankUpdated.getApprovedTransactions();

        //then
        List<Transaction> approvedTransactionsExpected = approvedTransactions.append(transaction);
        assertEquals(approvedTransactionsExpected, approvedTransactionsUpdated);
    }

    private List<Transaction> getApprovedTransactions() {
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionApproved1 = new Transaction("1", "Burger King", 20, dateTime);

        LocalDateTime dateTime2 = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 0, 0);
        Transaction transactionApproved2 = new Transaction("1", "Mac Donall's", 20, dateTime2);

        LocalDateTime dateTime3 = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 55, 0);
        Transaction transactionApproved3 = new Transaction("1", "Presto", 20, dateTime3);

        List<Transaction> approvedTransactions = List.of(transactionApproved1, transactionApproved2, transactionApproved3);

        return approvedTransactions;
    }
}
