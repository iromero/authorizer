package com.nubank.json;

import com.nubank.model.Transfer;
import com.nubank.operation.AccountOperation;
import com.nubank.operation.BankOperation;
import com.nubank.operation.TransactionOperation;
import com.nubank.model.Account;
import com.nubank.model.Transaction;
import com.nubank.operation.TransferOperation;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankOperationJsonBuilderFactoryTest {

    @Test
    public void testAccountDeserializer() {
        //given
        String json = "{\"account\":{\"accountId\":\"1\",\"active-card\":true,\"available-limit\":100},\"violations\":[]}";

        //when
        BankOperation bankOperation = new BankOperationJsonBuilderFactory().fromJson(json);

        //then
        assertTrue(bankOperation instanceof AccountOperation);
        AccountOperation operation = (AccountOperation) bankOperation;
        Account account = (Account) operation.getOperationInfo();
        assertEquals("1", account.getAccountId());
        assertEquals(100, account.getAvailableLimit());
        assertTrue(account.isActiveCard());
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
    public void testTransferDeserializer() {
        //given
        String json = "{\"transfer\":{\"accountId\":\"1\", \"source\":\"Davivienda Bank\",\"amount\": 100,\"time\":\"2019-02-13T10:00:00.000Z\"}}";

        //when
        BankOperation bankOperation = new BankOperationJsonBuilderFactory().fromJson(json);

        //then
        assertTrue(bankOperation instanceof TransferOperation);
        LocalDateTime time = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        TransferOperation operation = (TransferOperation) bankOperation;
        Transfer transfer = (Transfer) operation.getOperationInfo();
        assertEquals("1", transfer.getAccountId());
        assertEquals("Davivienda Bank", transfer.getSource());
        assertEquals(100, transfer.getAmount());
        assertEquals(time, transfer.getTime());
    }

    @Test
    public void testAccountSerializer() {
        //given
        Account account = new Account("1", true, 100);
        AccountOperation operation = new AccountOperation(account);

        //when
        String accountJson = new BankOperationJsonBuilderFactory().toJson(operation);

        //then
        String accountJsonExpected = "{\"account\":{\"accountId\":\"1\",\"active-card\":true,\"available-limit\":100},\"violations\":[]}";
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

    @Test
    public void testTransferSerializer() {
        //given
        Transfer transfer = new Transfer("1", "Davivienda Bank", 20, LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0));
        TransferOperation transferOperation = new TransferOperation(transfer);

        //when
        String transferJson = new BankOperationJsonBuilderFactory().toJson(transferOperation);

        //then
        String transactionJsonExpected = "{\"transfer\":{\"accountId\":\"1\",\"source\":\"Davivienda Bank\",\"amount\":20,\"time\":\"2019-02-13T10:00:00.000Z\"}}";
        assertEquals(transactionJsonExpected, transferJson);
    }

}