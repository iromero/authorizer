package com.nubank.service;

import com.nubank.model.Account;
import com.nubank.model.Violations;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessOutputOperationServiceTest {

    @Test
    public void testAccountOutputWithoutViolations() {
        //given
        Account account = new Account("1", true, 100);
        Violations violations = Violations.noViolations();

        //when
        ProcessOutputOperationService processOutputOperationService = new ProcessOutputOperationService(Option.of(account), violations);
        String jsonOutputResult = processOutputOperationService.process();

        //then
        String jsonOutputExpected = "{\"account\":{\"accountId\":\"1\",\"active-card\":true,\"available-limit\":100},\"violations\":[]}";
        assertEquals(jsonOutputExpected, jsonOutputResult);
    }

    @Test
    public void testAccountOutputWithViolations() {
        //given
        Account account = new Account("1", true, 100);
        Violations violations = new Violations(List.of("insufficient-limit", "high-frequency-small-interval"));

        //when
        ProcessOutputOperationService processOutputOperationService = new ProcessOutputOperationService(Option.of(account), violations);
        String jsonOutputResult = processOutputOperationService.process();

        //then
        String jsonOutputExpected = "{\"account\":{\"accountId\":\"1\",\"active-card\":true,\"available-limit\":100}," +
                "\"violations\":[\"insufficient-limit\",\"high-frequency-small-interval\"]}";
        assertEquals(jsonOutputExpected, jsonOutputResult);
    }

    @Test
    public void testNoAccountOutputWithViolations() {
        //given
        Option<Account> account = Option.none();//The account does not exist
        Violations violations = new Violations(List.of("account-not-initialized"));

        //when
        ProcessOutputOperationService processOutputOperationService = new ProcessOutputOperationService(account, violations);
        String jsonOutputResult = processOutputOperationService.process();

        //then
        String jsonOutputExpected = "{\"violations\":[\"account-not-initialized\"]}";
        assertEquals(jsonOutputExpected, jsonOutputResult);
    }
}
