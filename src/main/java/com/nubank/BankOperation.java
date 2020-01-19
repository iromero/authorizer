package com.nubank;

import com.nubank.visitor.TransactionVisitor;
import com.nubank.visitor.Visitable;

public abstract class BankOperation implements Visitable<TransactionVisitor> {
}
