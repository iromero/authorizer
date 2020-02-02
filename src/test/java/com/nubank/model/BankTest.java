package com.nubank.model;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {

    @Test
    public void testAccountInitialized() {
        //given
        Bank bank = Bank.init();
        Account account = new Account("1", true, 100);

        //when
        Bank bankUpdated = bank.update(account);

        //then
        assertEquals(account, bankUpdated.getCurrentAccount(account.getAccountId()).get());
    }

    @Test
    public void testAccountAvailableLimitAfterTransaction() {
        //given
        Bank bank = new Bank(getCurrentAccounts(), getEmptyTransactionsForCurrentAccounts());
        Transaction transaction = new Transaction("1", "Burger King", 20,
                LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0));

        //when
        Bank bankUpdated = bank.update(transaction);

        //then
        Account accountWithLimitUpdated = bankUpdated.getCurrentAccount(transaction.getAccountId()).get();
        assertEquals(new Account("1", true, 80), accountWithLimitUpdated);
    }

    @Test
    public void testApprovedTransactionsAfterTransaction() {
        //given
        List<Transaction> approvedTransactions = getApprovedTransactions().get("1").get();
        Bank bank = new Bank(getCurrentAccounts(), getApprovedTransactions());
        Transaction transaction = new Transaction("1", "Burger King", 20,
                LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0));

        //when
        Bank bankUpdated = bank.update(transaction);
        List<Transaction> approvedTransactionsUpdated = bankUpdated.getApprovedTransactions(transaction.getAccountId()).get();

        //then
        List<Transaction> approvedTransactionsExpected = approvedTransactions.append(transaction);
        assertEquals(approvedTransactionsExpected, approvedTransactionsUpdated);
    }

    @Test
    public void testAccountAvailableLimitAfterTransfer() {
        //given
        Bank bank = new Bank(getCurrentAccounts(), getEmptyTransactionsForCurrentAccounts(),
                getEmptyTransfersForCurrentAccounts());
        Transfer transfer = new Transfer("1", "Davivienda", 20,
                LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0));

        //when
        Bank bankUpdated = bank.update(transfer);

        //then
        Account accountWithLimitUpdated = bankUpdated.getCurrentAccount(transfer.getAccountId()).get();
        assertEquals(new Account("1", true, 120), accountWithLimitUpdated);
    }

    @Test
    public void testApprovedTransfersAfterTransaction() {
        //given
        List<Transfer> approvedTransfers = getApprovedTransfers().get("1").get();
        Bank bank = new Bank(getCurrentAccounts(), getApprovedTransactions(), getApprovedTransfers());
        Transfer transfer = new Transfer("1", "Scott Bank", 20,
                LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0));

        //when
        Bank bankUpdated = bank.update(transfer);
        List<Transfer> approvedTransfersUpdated = bankUpdated.getApprovedTransfers(transfer.getAccountId()).get();

        //then
        List<Transfer> approvedTransfersExpected = approvedTransfers.append(transfer);
        assertEquals(approvedTransfersExpected, approvedTransfersUpdated);
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

    private Map<String, List<Transfer>> getApprovedTransfers() {
        LocalDateTime dateTime = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 0, 0, 0);
        Transfer transferApproved1 = new Transfer("1", "Davivienda Bank", 20, dateTime);

        LocalDateTime dateTime2 = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 0, 0);
        Transfer transferApproved2 = new Transfer("1", "City Bank", 20, dateTime2);

        LocalDateTime dateTime3 = LocalDateTime.of(2019, Month.FEBRUARY, 13, 10, 1, 55, 0);
        Transfer transferApproved3 = new Transfer("1", "Santander Bank", 20, dateTime3);

        return HashMap.ofEntries(
                Map.entry("1", List.of(transferApproved1, transferApproved2, transferApproved3)),
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

    private Map<String, List<Transfer>> getEmptyTransfersForCurrentAccounts() {
        return HashMap.ofEntries(
                Map.entry("1", List.empty()),
                Map.entry("2", List.empty()),
                Map.entry("3", List.empty())
        );
    }

}
