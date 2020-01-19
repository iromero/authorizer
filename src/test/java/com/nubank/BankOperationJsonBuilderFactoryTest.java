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
        String json = "{\"activeCard\":true,\"availableLimit\":\"100\"}";

        //when
        BankOperation fromJsonBuilder = new BankOperationJsonBuilderFactory().fromJson(json);

        //then
        assertTrue(fromJsonBuilder instanceof Account);
    }

    @Test
    public void transactionDeserializer() {
        //given
        String json = "{\"transaction\":{\"merchant\":true,\"amount\":\"20\",\"time\":\"2019-02-13T10:00:00.000Z\"}}";

        //when
        BankOperation fromJsonBuilder = new BankOperationJsonBuilderFactory().fromJson(json);

        //then
        assertTrue(fromJsonBuilder instanceof Transaction);
    }

    @Test
    public void accountSerializer() {
        //given
        BankOperation account = new Account(true, 100);
        String accountJsonExpected = "{\"activeCard\":true,\"availableLimit\":100,\"violations\":[]}";

        //when
        String accountJson = new BankOperationJsonBuilderFactory().toJson(account);

        //then
        assertEquals(accountJsonExpected, accountJson);
    }

//    @Test
//    public void transactionSerializer() {
//        //given
//        Transaction transaction = new Transaction("Burger King", 20, LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0));
//
//        //when
//        String transactionJson = new BankOperationJsonBuilderFactory().toJson(transaction);
//
//        //then
//        String transactionJsonExpected = "{\"transaction\":{\"merchant\": \"Burger King\" ,\"amount\":\"20\",\"time\":\"2019-02-13T10:00:00.000Z\"}}";
//        assertEquals(transactionJsonExpected, transactionJson);
//    }

}
