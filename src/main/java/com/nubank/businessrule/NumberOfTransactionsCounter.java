package com.nubank.businessrule;

public class NumberOfTransactionsCounter {
    private int counter = 0;

    public NumberOfTransactionsCounter(int counter) {
        this.counter = counter;
    }

    public NumberOfTransactionsCounter increment() {
        return new NumberOfTransactionsCounter(counter + 1);
    }

    public boolean isCounterBiggerEqualThan(int number) {
        return (counter >= number);
    }
}
