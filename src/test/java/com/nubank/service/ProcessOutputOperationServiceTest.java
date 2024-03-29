package com.nubank.service;

import com.nubank.model.Account;
import com.nubank.model.Violations;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessOutputOperationServiceTest {

    @Test
    public void testAccountOutputWithoutViolations() {
        //given
        Account account = new Account(true, 100);
        Violations violations = Violations.noViolations();

        //when
        ProcessOutputOperationService processOutputOperationService = new ProcessOutputOperationService(account, violations);
        String jsonOutputResult = processOutputOperationService.process();

        //then
        String jsonOutputExpected = "{\"account\":{\"active-card\":true,\"available-limit\":100},\"violations\":[]}";
        assertEquals(jsonOutputExpected, jsonOutputResult);
    }

    @Test
    public void testAccountOutputWithViolations() {
        //given
        Account account = new Account(true, 100);
        Violations violations = new Violations(List.of("insufficient-limit", "high-frequency-small-interval"));

        //when
        ProcessOutputOperationService processOutputOperationService = new ProcessOutputOperationService(account, violations);
        String jsonOutputResult = processOutputOperationService.process();

        //then
        String jsonOutputExpected = "{\"account\":{\"active-card\":true,\"available-limit\":100}," +
                "\"violations\":[\"insufficient-limit\",\"high-frequency-small-interval\"]}";
        assertEquals(jsonOutputExpected, jsonOutputResult);
    }
}
