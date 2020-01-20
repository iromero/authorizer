package com.nubank;

import com.nubank.model.Account;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;
import com.nubank.service.TransactionAuthorizationService;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import io.vavr.collection.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionAuthorizationServiceTest {

    @Test
    public void noViolations() {
        //given
        Account currentAccount = new Account(true, 100);
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("Burger King", 20, dateTime);

        //when
        Violations violations = new TransactionAuthorizationService(currentAccount, List.empty(),
                transactionToBeApproved).evalTransaction();

        //then
        assertEquals(Violations.noViolations(), violations);
    }

    @Test
    public void accountNoInitialized() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("Burger King", 20, dateTime);

        //when
        Violations violations = new TransactionAuthorizationService(null, List.empty(),
                transactionToBeApproved).evalTransaction();

        //then
        assertEquals(Violations.accountNotInitialized(), violations);
    }

    @Test
    public void cardNotActive() {
        //given
        Account currentAccount = new Account(false, 100);
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("Burger King", 20, dateTime);

        //when
        Violations violations = new TransactionAuthorizationService(currentAccount, List.empty(),
                transactionToBeApproved).evalTransaction();

        //then
        assertEquals(Violations.accountWithCardNotActive(), violations);
    }

    @Test
    public void insufficientLimit() {
        //given
        Account currentAccount = new Account(true, 80);
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("Burger King", 90, dateTime);

        //when
        Violations violations = new TransactionAuthorizationService(currentAccount, List.empty(),
                transactionToBeApproved).evalTransaction();

        //then
        assertEquals(Violations.accountWithInsufficientLimits(), violations);
    }

    @Test
    public void highFrequencySmallInterval() {
        //given
        Account currentAccount = new Account(true, 100);
        List<Transaction> approvedTransactions = getApprovedTransactions();
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 30, 0);
        Transaction transactionToBeApproved = new Transaction("Grills", 20, dateTime);

        //when
        Violations violations = new TransactionAuthorizationService(currentAccount, approvedTransactions, transactionToBeApproved).evalTransaction();

        //then
        assertEquals(Violations.accountWithHighFrequencySmallInterval(), violations);
    }

    @Test
    public void doubledTransaction() {
        //given
        Account currentAccount = new Account(true, 100);
        List<Transaction> approvedTransactions = getApprovedTransactions();
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 2, 55, 0);
        Transaction transactionToBeApproved = new Transaction("Mac Donall's", 20, dateTime);

        //when
        Violations violations = new TransactionAuthorizationService(currentAccount, approvedTransactions, transactionToBeApproved).evalTransaction();

        //then
        assertEquals(Violations.accountWithDoubleTransaction(), violations);
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
