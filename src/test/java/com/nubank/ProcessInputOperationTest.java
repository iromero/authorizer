package com.nubank;

import com.nubank.model.Account;
import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;
import com.nubank.service.ProcessInputOperation;
import com.nubank.service.ProcessInputOperationResult;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessInputOperationTest {

    @Test
    public void noViolationWhenTryingAccountCreation() {
        //given
        Bank bank = Bank.init();
        String accountOperationJson = "{\"account\": {\"active-card\": true, \"available-limit\": 100}}";

        //when
        ProcessInputOperation processInputOperation = new ProcessInputOperation(bank, accountOperationJson);
        ProcessInputOperationResult result = processInputOperation.process();

        //then
        assertEquals(Violations.noViolations(), result.getViolations());
    }

    @Test
    public void accountAlreadyInitializedWhenTryingAccountCreation() {
        //given
        Bank bank = new Bank(new Account(true, 100), List.empty());
        String accountOperationJson = "{\"account\": {\"active-card\": true, \"available-limit\": 100}}";

        //when
        ProcessInputOperation processInputOperation = new ProcessInputOperation(bank, accountOperationJson);
        ProcessInputOperationResult result = processInputOperation.process();

        //then
        assertEquals(Violations.accountAlreadyInitialized(), result.getViolations());
    }

    @Test
    public void noViolationsWhenTryingTransactionAuthorization() {
        //given
        Account currentAccount = new Account(true, 100);
        Bank bank = new Bank(currentAccount, List.empty());
        String transactionOperationJson = "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\"}}";

        //when
        ProcessInputOperation processInputOperation = new ProcessInputOperation(bank, transactionOperationJson);
        ProcessInputOperationResult result = processInputOperation.process();

        //then
        assertEquals(Violations.noViolations(), result.getViolations());
    }

    @Test
    public void accountNoInitializedWhenTryingTransactionAuthorization() {
        //given
        Bank bank = Bank.init();//The account is not initialized.
        String transactionOperationJson = "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\"}}";

        //when
        ProcessInputOperation processInputOperation = new ProcessInputOperation(bank, transactionOperationJson);
        ProcessInputOperationResult result = processInputOperation.process();

        //then
        assertEquals(Violations.accountNotInitialized(), result.getViolations());
    }

    @Test
    public void cardNotActiveWhenTryingTransactionAuthorization() {
        //given
        Account currentAccount = new Account(false, 100);
        Bank bank = new Bank(currentAccount, List.empty());
        String transactionOperationJson = "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\"}}";

        //when
        ProcessInputOperation processInputOperation = new ProcessInputOperation(bank, transactionOperationJson);
        ProcessInputOperationResult result = processInputOperation.process();

        //then
        assertEquals(Violations.accountWithCardNotActive(), result.getViolations());
    }

    @Test
    public void insufficientLimitWhenTryingTransactionAuthorization() {
        //given
        Account currentAccount = new Account(true, 80);
        Bank bank = new Bank(currentAccount, List.empty());
        String transactionOperationJson = "{\"transaction\": {\"merchant\": \"Burger King\", \"amount\": 90, \"time\": \"2019-02-13T10:00:00.000Z\"}}";

        //when
        ProcessInputOperation processInputOperation = new ProcessInputOperation(bank, transactionOperationJson);
        ProcessInputOperationResult result = processInputOperation.process();

        //then
        assertEquals(Violations.accountWithInsufficientLimits(), result.getViolations());
    }

    @Test
    public void highFrequencySmallIntervalWhenTryingTransactionAuthorization() {
        //given
        Account currentAccount = new Account(true, 100);
        List<Transaction> approvedTransactions = getApprovedTransactions();
        Bank bank = new Bank(currentAccount, approvedTransactions);
        String transactionOperationJson = "{\"transaction\": {\"merchant\": \"Grills\", \"amount\": 20, \"time\": \"2019-02-13T10:01:30.000Z\"}}";

        //when
        ProcessInputOperation processInputOperation = new ProcessInputOperation(bank, transactionOperationJson);
        ProcessInputOperationResult result = processInputOperation.process();

        //then
        assertEquals(Violations.accountWithHighFrequencySmallInterval(), result.getViolations());
    }

    @Test
    public void doubledTransactionWhenTryingTransactionAuthorization() {
        //given
        Account currentAccount = new Account(true, 100);
        List<Transaction> approvedTransactions = getApprovedTransactions();
        Bank bank = new Bank(currentAccount, approvedTransactions);
        String transactionOperationJson = "{\"transaction\": {\"merchant\": \"Mac Donall's\", \"amount\": 20, \"time\": \"2019-02-13T10:02:55.000Z\"}}";

        //when
        ProcessInputOperation processInputOperation = new ProcessInputOperation(bank, transactionOperationJson);
        ProcessInputOperationResult result = processInputOperation.process();

        //then
        assertEquals(Violations.accountWithDoubleTransaction(), result.getViolations());
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