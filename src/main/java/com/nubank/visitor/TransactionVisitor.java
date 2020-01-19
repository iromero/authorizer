package com.nubank.visitor;

import com.nubank.Account;
import com.nubank.Transaction;

public interface TransactionVisitor {
    void visit(Account account);

    void visit(Transaction transaction);
}