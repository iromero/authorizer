package com.nubank;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankOperationJsonBuilderFactoryTest {

    @Test
    public void accountDeserializer() {
        //given
        String json = "{\"account\":{\"active-card\":true,\"available-limit\":100},\"violations\":[]}";

        //when
        BankOperation bankOperation = new BankOperationJsonBuilderFactory().fromJson(json);

        //then
        assertTrue(bankOperation instanceof AccountOperation);
        AccountOperation operation = (AccountOperation) bankOperation;
        Account account = (Account) operation.getOperationInfo();
        assertEquals(100, account.getAvailableLimit());
        assertEquals(true, account.isActiveCard());
    }

    @Test
    public void transactionDeserializer() {
        //given
        String json = "{\"transaction\":{\"merchant\":\"Burger King\",\"amount\": 20,\"time\":\"2019-02-13T10:00:00.000Z\"}}";

        //when
        BankOperation bankOperation = new BankOperationJsonBuilderFactory().fromJson(json);

        //then
        assertTrue(bankOperation instanceof TransactionOperation);
        LocalDateTime time = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        TransactionOperation operation = (TransactionOperation) bankOperation;
        Transaction transaction = (Transaction) operation.getOperationInfo();
        assertEquals("Burger King", transaction.getMerchant());
        assertEquals(20, transaction.getAmount());
        assertEquals(time, transaction.getTime());
    }

    @Test
    public void accountSerializer() {
        //given
        Account account = new Account(true, 100);
        AccountOperation operation = new AccountOperation(account);

        //when
        String accountJson = new BankOperationJsonBuilderFactory().toJson(operation);

        //then
        String accountJsonExpected = "{\"account\":{\"active-card\":true,\"available-limit\":100},\"violations\":[]}";
        assertEquals(accountJsonExpected, accountJson);
    }

    @Test
    public void transactionSerializer() {
        //given
        Transaction transaction = new Transaction("Burger King", 20, LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0));
        TransactionOperation transactionOperation = new TransactionOperation(transaction);

        //when
        String transactionJson = new BankOperationJsonBuilderFactory().toJson(transactionOperation);

        //then
        String transactionJsonExpected = "{\"transaction\":{\"merchant\":\"Burger King\",\"amount\":20,\"time\":\"2019-02-13T10:00:00.000Z\"}}";
        assertEquals(transactionJsonExpected, transactionJson);
    }

}
