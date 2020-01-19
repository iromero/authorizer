package com.nubank;

import com.nubank.visitor.BankOperationFromJsonBuilderFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankOperationFromJsonBuilderFactoryTest {

    @Test
    public void accountDeserializerFromJson() {
        //given
        String json = "{\"activeCard\":true,\"availableLimit\":\"100\"}";

        //when
        BankOperation fromJsonBuilder = new BankOperationFromJsonBuilderFactory().buildObject(json);

        //then
        assertTrue(fromJsonBuilder instanceof Account);
    }

    @Test
    public void transactionDeserializerFromJson() {
        //given
        String json = "{\"transaction\":{\"merchant\":true,\"amount\":\"20\",\"time\":\"2019-02-13T10:00:00.000Z\"}}";

        //when
        BankOperation fromJsonBuilder = new BankOperationFromJsonBuilderFactory().buildObject(json);

        //then
        assertTrue(fromJsonBuilder instanceof Transaction);
    }

}
