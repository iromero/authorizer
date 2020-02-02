package com.nubank.businessrule;

public class MaxAmountAccumulator {
    private final int accum;

    public MaxAmountAccumulator(int accum) {
        this.accum = accum;
    }

    public MaxAmountAccumulator increment(int amount) {
        return new MaxAmountAccumulator(accum + amount);
    }

    public int getAmount() {
        return accum;
    }
}