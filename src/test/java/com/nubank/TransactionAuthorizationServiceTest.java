package com.nubank;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionAuthorizationServiceTest {

    @Test
    public void transactionApproved() {
        Account currentAccount = new Account(true, 100);
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("Burger King", 20, dateTime);
        Account accountStatus = new TransactionAuthorizationService(currentAccount, transactionToBeApproved).evalTransaction();
        Account accountExpected = new Account(true, 80);
        assertEquals(accountExpected, accountStatus);
    }

    @Test
    public void accountNoInitialized() {
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("Burger King", 20, dateTime);
        Account accountStatus = new TransactionAuthorizationService(null, transactionToBeApproved).evalTransaction();
        Account accountExpected = Account.accountNotInitialized();
        assertEquals(accountExpected, accountStatus);
    }

    public void cardNotActive() {
        Account currentAccount = new Account(false, 100);
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionToBeApproved = new Transaction("Burger King", 20, dateTime);
        Account accountStatus = new TransactionAuthorizationService(null, transactionToBeApproved).evalTransaction();
        Account accountExpected = Account.accountNotInitialized();
        assertEquals(accountExpected, accountStatus);
    }

    public void insufficientLimit() {

    }

    public void highFrequencySmallInterval() {

    }

    public void doubledTransaction() {

    }


}
