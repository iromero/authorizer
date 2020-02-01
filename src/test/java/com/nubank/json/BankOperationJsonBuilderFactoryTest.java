package com.nubank.json;

import com.nubank.operation.AccountOperation;
import com.nubank.operation.BankOperation;
import com.nubank.operation.TransactionOperation;
import com.nubank.model.Account;
import com.nubank.model.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankOperationJsonBuilderFactoryTest {

    @Test
    public void testAccountDeserializer() {
        //given
        String json = "{\"account\":{\"id\":\"1\",\"active-card\":true,\"available-limit\":100},\"violations\":[]}";

        //when
        BankOperation bankOperation = new BankOperationJsonBuilderFactory().fromJson(json);

        //then
        assertTrue(bankOperation instanceof AccountOperation);
        AccountOperation operation = (AccountOperation) bankOperation;
        Account account = (Account) operation.getOperationInfo();
        assertEquals("1", account.getId());
        assertEquals(100, account.getAvailableLimit());
        assertEquals(true, account.isActiveCard());
    }

    @Test
    public void testTransactionDeserializer() {
        //given
        String json = "{\"transaction\":{\"accountId\":\"1\", \"merchant\":\"Burger King\",\"amount\": 20,\"time\":\"2019-02-13T10:00:00.000Z\"}}";

        //when
        BankOperation bankOperation = new BankOperationJsonBuilderFactory().fromJson(json);

        //then
        assertTrue(bankOperation instanceof TransactionOperation);
        LocalDateTime time = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        TransactionOperation operation = (TransactionOperation) bankOperation;
        Transaction transaction = (Transaction) operation.getOperationInfo();
        assertEquals("1", transaction.getAccountId());
        assertEquals("Burger King", transaction.getMerchant());
        assertEquals(20, transaction.getAmount());
        assertEquals(time, transaction.getTime());
    }

    @Test
    public void testAccountSerializer() {
        //given
        Account account = new Account("1", true, 100);
        AccountOperation operation = new AccountOperation(account);

        //when
        String accountJson = new BankOperationJsonBuilderFactory().toJson(operation);

        //then
        String accountJsonExpected = "{\"account\":{\"id\":\"1\",\"active-card\":true,\"available-limit\":100},\"violations\":[]}";
        assertEquals(accountJsonExpected, accountJson);
    }

    @Test
    public void testTransactionSerializer() {
        //given
        Transaction transaction = new Transaction("1", "Burger King", 20, LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0));
        TransactionOperation transactionOperation = new TransactionOperation(transaction);

        //when
        String transactionJson = new BankOperationJsonBuilderFactory().toJson(transactionOperation);

        //then
        String transactionJsonExpected = "{\"transaction\":{\"accountId\":\"1\",\"merchant\":\"Burger King\",\"amount\":20,\"time\":\"2019-02-13T10:00:00.000Z\"}}";
        assertEquals(transactionJsonExpected, transactionJson);
    }

}