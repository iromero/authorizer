package com.nubank;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

    private final String merchant;
    private final int amount;
    private final LocalDateTime time;
    private final List<String> violations;

    public Transaction(String merchant, int amount, LocalDateTime time) {
        this.merchant = merchant;
        this.amount = amount;
        this.time = time;
        this.violations = new ArrayList<String>();
    }

    public int getAmount() {
        return amount;
    }
}
