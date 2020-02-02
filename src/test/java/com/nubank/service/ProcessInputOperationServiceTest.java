package com.nubank.service;

import com.nubank.model.Account;
import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProcessInputOperationServiceTest {

    @Test
    void testNoViolationWhenTryingAccountCreation() {
        //given
        Bank bank = Bank.init();
        String accountOperationJson = "{\"account\": {\"accountId\":\"1\",\"active-card\": true, \"available-limit\": 100}}";

        //when
        ProcessInputOperationService processInputOperationService = new ProcessInputOperationService(bank, accountOperationJson);
        ProcessInputOperationResult result = processInputOperationService.process();

        //then
        assertEquals(Violations.noViolations(), result.getViolations());
    }

    @Test
    void testAccountAlreadyInitializedWhenTryingAccountCreation() {
        //given
        Bank bank = new Bank(getCurrentAccounts(), getEmptyTransactionsForCurrentAccounts());
        String accountOperationJson = "{\"account\": {\"accountId\":\"1\",\"active-card\": true, \"available-limit\": 100}}";

        //when
        ProcessInputOperationService processInputOperationService = new ProcessInputOperationService(bank, accountOperationJson);
        ProcessInputOperationResult result = processInputOperationService.process();

        //then
        assertEquals(Violations.accountAlreadyInitialized(), result.getViolations());
    }

    @Test
    void testNoViolationsWhenTryingTransactionAuthorization() {
        //given
        Account currentAccount = new Account("1", true, 100);
        Bank bank = new Bank(getCurrentAccounts(), getEmptyTransactionsForCurrentAccounts());
        String transactionOperationJson = "{\"transaction\": {\"accountId\":\"1\", \"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\"}}";

        //when
        ProcessInputOperationService processInputOperationService = new ProcessInputOperationService(bank, transactionOperationJson);
        ProcessInputOperationResult result = processInputOperationService.process();

        //then
        assertEquals(Violations.noViolations(), result.getViolations());
    }

    @Test
    void testAccountNoInitializedWhenTryingTransactionAuthorization() {
        //given
        Bank bank = Bank.init();//The account is not initialized.
        String transactionOperationJson = "{\"transaction\": {\"accountId\":\"1\", \"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\"}}";

        //when
        ProcessInputOperationService processInputOperationService = new ProcessInputOperationService(bank, transactionOperationJson);
        ProcessInputOperationResult result = processInputOperationService.process();

        //then
        assertEquals(Violations.accountNotInitialized(), result.getViolations());
    }

    @Test
    void testCardNotActiveWhenTryingTransactionAuthorization() {
        //given
        Map<String, Account> currentAccounts = HashMap.ofEntries(Map.entry("1", new Account("1", false, 100)));
        Bank bank = new Bank(currentAccounts, getEmptyTransactionsForCurrentAccounts());
        String transactionOperationJson = "{\"transaction\": {\"accountId\":\"1\", \"merchant\": \"Burger King\", \"amount\": 20, \"time\": \"2019-02-13T10:00:00.000Z\"}}";

        //when
        ProcessInputOperationService processInputOperationService = new ProcessInputOperationService(bank, transactionOperationJson);
        ProcessInputOperationResult result = processInputOperationService.process();

        //then
        assertEquals(Violations.accountWithCardNotActive(), result.getViolations());
    }

    @Test
    void testInsufficientLimitWhenTryingTransactionAuthorization() {
        //given
        Bank bank = new Bank(getCurrentAccounts(), getEmptyTransactionsForCurrentAccounts());
        String transactionOperationJson = "{\"transaction\": {\"accountId\":\"1\", \"merchant\": \"Burger King\", \"amount\": 110, \"time\": \"2019-02-13T10:00:00.000Z\"}}";

        //when
        ProcessInputOperationService processInputOperationService = new ProcessInputOperationService(bank, transactionOperationJson);
        ProcessInputOperationResult result = processInputOperationService.process();

        //then
        assertEquals(Violations.accountWithInsufficientLimits(), result.getViolations());
    }

    @Test
    void testHighFrequencySmallIntervalWhenTryingTransactionAuthorization() {
        //given
        Bank bank = new Bank(getCurrentAccounts(), getApprovedTransactions());
        String transactionOperationJson = "{\"transaction\": {\"accountId\":\"1\", \"merchant\": \"Grills\", \"amount\": 20, \"time\": \"2019-02-13T10:01:30.000Z\"}}";

        //when
        ProcessInputOperationService processInputOperationService = new ProcessInputOperationService(bank, transactionOperationJson);
        ProcessInputOperationResult result = processInputOperationService.process();

        //then
        assertEquals(Violations.accountWithHighFrequencySmallInterval(), result.getViolations());
    }

    @Test
    void testDoubledTransactionWhenTryingTransactionAuthorization() {
        //given
        Bank bank = new Bank(getCurrentAccounts(), getApprovedTransactions());
        String transactionOperationJson = "{\"transaction\": {\"accountId\":\"1\", \"merchant\": \"Mac Donall's\", \"amount\": 20, \"time\": \"2019-02-13T10:02:55.000Z\"}}";

        //when
        ProcessInputOperationService processInputOperationService = new ProcessInputOperationService(bank, transactionOperationJson);
        ProcessInputOperationResult result = processInputOperationService.process();

        //then
        assertEquals(Violations.accountWithDoubleTransaction(), result.getViolations());
    }

    private Map<String, List<Transaction>> getApprovedTransactions() {
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transaction transactionApproved1 = new Transaction("1", "Burger King", 20, dateTime);

        LocalDateTime dateTime2 = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 0, 0);
        Transaction transactionApproved2 = new Transaction("1", "Mac Donall's", 20, dateTime2);

        LocalDateTime dateTime3 = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 55, 0);
        Transaction transactionApproved3 = new Transaction("1", "Presto", 20, dateTime3);

        return HashMap.ofEntries(
                Map.entry("1", List.of(transactionApproved1, transactionApproved2, transactionApproved3)),
                Map.entry("2", List.empty()),
                Map.entry("3", List.empty())
        );
    }

    private Map<String, Account> getCurrentAccounts() {
        return HashMap.ofEntries(
                Map.entry("1", new Account("1", true, 100)),
                Map.entry("2", new Account("2", true, 150)),
                Map.entry("3", new Account("3", false, 100))
        );
    }

    private Map<String, List<Transaction>> getEmptyTransactionsForCurrentAccounts() {
        return HashMap.ofEntries(
                Map.entry("1", List.empty()),
                Map.entry("2", List.empty()),
                Map.entry("3", List.empty())
        );
    }
}