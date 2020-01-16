package com.nubank;

import java.time.LocalDateTime;

public class TransactionAuthorizationService {

    private final Account currentAcccount;
    private final Transaction transactionToBeAproved;

    public TransactionAuthorizationService(Account currentAcccount, Transaction transactionToBeAproved) {
        this.currentAcccount = currentAcccount;
        this.transactionToBeAproved = transactionToBeAproved;
    }

    public Account evalTransaction() {
        return currentAcccount.debt(transactionToBeAproved);
    }
}
