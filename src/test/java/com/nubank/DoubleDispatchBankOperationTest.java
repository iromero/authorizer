package com.nubank;

import com.nubank.visitor.TransactionCreator;
import com.nubank.visitor.TransactionVisitor;

public class DoubleDispatchBankOperationTest {
    void test(){
        //given
        Bank bank = new Bank();
        BankOperation bankOperation = new Account(true, 100);
        TransactionVisitor visitor = new TransactionCreator(bank);

        //when

        //then
    }

}
