package com.nubank;

import com.nubank.visitor.BankOperationFromJsonBuilderFactory;
import com.nubank.visitor.TransactionCreator;
import com.nubank.visitor.TransactionVisitor;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.Scanner;

public class AuthorizerApplication implements Runnable {

    volatile boolean keepRunning = true;

    public void run() {
//        System.out.println("Starting to loop.");
        while (keepRunning) {
//            System.out.println("Running loop...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
//        System.out.println("Done looping.");
    }

    public static void main(String[] args) {
        AuthorizerApplication authorizerApplication = new AuthorizerApplication();

        Bank bank = new Bank();

        Thread t = new Thread(authorizerApplication);
        t.start();

        Scanner s = new Scanner(System.in);
        String next = null;
        while (s.hasNext() && !(next = s.next()).equals("stop")) {
            try {
                String bankOperationJson = FileUtils.readFileToString(FileUtils.getFile(next), "UTF-8");
                TransactionVisitor visitor = new TransactionCreator(bank);
                BankOperation bankOperation = new BankOperationFromJsonBuilderFactory().buildObject(bankOperationJson);
                bankOperation.accept(visitor);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(next);
        }

        authorizerApplication.keepRunning = false;
        t.interrupt();  // cancel current sleep.
    }

}
