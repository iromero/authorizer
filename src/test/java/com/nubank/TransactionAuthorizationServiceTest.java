package com.nubank;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import io.vavr.collection.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionAuthorizationServiceTest {

    @Test
    public void transactionApproved() {
        //given
        Account currentAccount = new Account(true, 100);
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("Burger King", 20, dateTime);

        //when
        Account accountStatus = new TransactionAuthorizationService(currentAccount, List.empty(),
                transactionToBeApproved).evalTransaction();

        //then
        Account accountExpected = new Account(true, 80);
        assertEquals(accountExpected, accountStatus);
    }

    @Test
    public void accountNoInitialized() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("Burger King", 20, dateTime);

        //when
        Account accountStatus = new TransactionAuthorizationService(null, List.empty(),
                transactionToBeApproved).evalTransaction();

        //then
        Account accountExpected = Account.accountNotInitialized();
        assertEquals(accountExpected, accountStatus);
    }

    @Test
    public void cardNotActive() {
        //given
        Account currentAccount = new Account(false, 100);
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("Burger King", 20, dateTime);

        //when
        Account accountStatus = new TransactionAuthorizationService(currentAccount, List.empty(),
                transactionToBeApproved).evalTransaction();

        //then
        Account accountExpected = currentAccount.accountWithCardNotActive();
        assertEquals(accountExpected, accountStatus);
    }

    @Test
    public void insufficientLimit() {
        //given
        Account currentAccount = new Account(true, 80);
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("Burger King", 90, dateTime);

        //when
        Account accountStatus = new TransactionAuthorizationService(currentAccount, List.empty(),
                transactionToBeApproved).evalTransaction();

        //then
        Account accountExpected = currentAccount.accountWithInsufficientLimits();
        assertEquals(accountExpected, accountStatus);
    }

    @Test
    public void highFrequencySmallInterval() {
        //given
        Account currentAccount = new Account(true, 100);
        List<Transaction> approvedTransactions = getApprovedTransactions();
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 30, 0);
        Transaction transactionToBeApproved = new Transaction("Grills", 20, dateTime);

        //when
        Account resultAuthorizationStatus = new TransactionAuthorizationService(currentAccount, approvedTransactions, transactionToBeApproved).evalTransaction();

        //then
        Account expectedAuthorization = currentAccount.accountWithHighFrequencySmallInterval();
        assertEquals(expectedAuthorization, resultAuthorizationStatus);
    }

    @Test
    public void doubledTransaction() {
        //given
        Account currentAccount = new Account(true, 100);
        List<Transaction> approvedTransactions = getApprovedTransactions();
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 59, 0);
        Transaction transactionToBeApproved = new Transaction("Burger King", 20, dateTime);

        //when
        Account resultAuthorizationStatus = new TransactionAuthorizationService(currentAccount, approvedTransactions, transactionToBeApproved).evalTransaction();

        //then
        Account expectedAuthorization = currentAccount.accountWithDoubleTransaction();
        assertEquals(expectedAuthorization, resultAuthorizationStatus);
    }

    private List<Transaction> getApprovedTransactions() {
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionApproved1 = new Transaction("Burger King", 20, dateTime);

        LocalDateTime dateTime2 = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 0, 0);
        Transaction transactionApproved2 = new Transaction("Mac Donall's", 20, dateTime2);

        LocalDateTime dateTime3 = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 55, 0);
        Transaction transactionApproved3 = new Transaction("Presto", 20, dateTime3);

        List<Transaction> approvedTransactions = List.of(transactionApproved1, transactionApproved2, transactionApproved3);

        return approvedTransactions;
    }
}
