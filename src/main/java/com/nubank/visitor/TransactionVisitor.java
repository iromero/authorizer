package com.nubank.visitor;

import com.nubank.Account;
import com.nubank.Transaction;

public interface TransactionVisitor {
    Account visit(Account account);

    Account visit(Transaction transaction);
}