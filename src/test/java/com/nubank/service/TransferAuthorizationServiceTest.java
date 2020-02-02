package com.nubank.service;

import com.nubank.model.*;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferAuthorizationServiceTest {


    @Test
    public void testNoViolations() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transfer transferToBeApproved = new Transfer("1", "Davivienda Bank", 20, dateTime);
        Bank bank = new Bank(getCurrentAccounts(), getEmptyTransfersForCurrentAccounts());

        //when
        Violations violations = new TransferAuthorizationService(bank, transferToBeApproved).evalOperation();

        //then
        assertEquals(Violations.noViolations(), violations);
    }

    @Test
    public void testAccountNoInitialized() {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transfer transferToBeApproved = new Transfer("1", "Burger King", 20, dateTime);
        Bank bank = Bank.init();//The account is not initialized.

        //when
        Violations violations = new TransferAuthorizationService(bank, transferToBeApproved).evalOperation();

        //then
        assertEquals(Violations.accountNotInitialized(), violations);
    }


    private Map<String, List<Transaction>> getEmptyTransfersForCurrentAccounts() {
        return HashMap.ofEntries(
                Map.entry("1", List.empty()),
                Map.entry("2", List.empty()),
                Map.entry("3", List.empty())
        );
    }

    private Map<String, Account> getCurrentAccounts() {
        return HashMap.ofEntries(
                Map.entry("1", new Account("1", true, 200)),
                Map.entry("2", new Account("2", true, 50)),
                Map.entry("3", new Account("3", false, 100))
        );
    }

    private Map<String, List<Transfer>> getApprovedTransfers() {
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transfer transferApproved1 = new Transfer("1", "Burger King", 20, dateTime);

        LocalDateTime dateTime2 = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 0, 0);
        Transfer transferApproved2 = new Transfer("1", "Mac Donall's", 20, dateTime2);

        LocalDateTime dateTime3 = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 55, 0);
        Transfer transferApproved3 = new Transfer("1", "Presto", 20, dateTime3);

        return HashMap.ofEntries(
                Map.entry("1", List.of(transferApproved1, transferApproved2, transferApproved3)),
                Map.entry("2", List.empty()),
                Map.entry("3", List.empty())
        );
    }

}
