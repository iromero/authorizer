package com.nubank;

import io.vavr.collection.List;

import java.util.Scanner;

public class Bank implements Runnable {
    private Account account;
    private List<Transaction> transactions;

    public Account createAccount(Account account) {
        return null;
    }

    private Transaction createTransaction(Transaction transaction) {
        return null;
    }

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
        Bank bank = new Bank();
        Thread t = new Thread(bank);
        t.start();

        Scanner s = new Scanner(System.in);
        String next = null;
        while (s.hasNext() && !(next = s.next()).equals("stop")) {
            System.out.println(next);
        }

        bank.keepRunning = false;
        t.interrupt();  // cancel current sleep.
    }

}
