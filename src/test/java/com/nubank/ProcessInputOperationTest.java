package com.nubank;

import com.nubank.model.Bank;
import com.nubank.model.Violations;
import com.nubank.service.ProcessInputOperation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessInputOperationTest {

    @Test
    public void accountCreationNoViolation() {
        //given
        String accountOperationJson = "{\"account\": {\"active-card\": true, \"available-limit\": 100}}";
        Bank bank = Bank.init();

        //when
        ProcessInputOperation processInputOperation = new ProcessInputOperation(bank, accountOperationJson);
        Violations violations = processInputOperation.process();

        //then
        assertEquals(Violations.noViolations(), violations);
    }
}
