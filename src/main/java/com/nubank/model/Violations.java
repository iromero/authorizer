package com.nubank.model;

import io.vavr.collection.List;

import java.util.Objects;

public class Violations {
    private static final String ACCOUNT_ALREADY_INITIALIZED = "account-already-initialized";
    private static final String ACCOUNT_NOT_INITIALIZED = "account-not-initialized";
    private static final String INSUFFICIENT_LIMIT = "insufficient-limit";
    private static final String CARD_NOT_ACTIVE = "card-not-active";
    private static final String HIGH_FREQUENCY_SMALL_INTERVAL = "high-frequency-small-interval";
    private static final String DOUBLE_TRANSACTION = "double-transaction";
    private final List<String> violations;


    public Violations(List<String> violations) {
        this.violations = violations;
    }

    public Violations() {
        violations = List.empty();
    }

    public List<String> getViolations() {
        return violations;
    }

    public static Violations noViolations() {
        return new Violations(List.empty());
    }

    public static Violations accountAlreadyInitialized() {
        return new Violations(List.of(ACCOUNT_ALREADY_INITIALIZED));
    }

    public Violations appendAccountAlreadyInitialized() {
        return new Violations(violations.append(ACCOUNT_ALREADY_INITIALIZED));
    }

    public static Violations accountNotInitialized() {
        return new Violations(List.of(ACCOUNT_NOT_INITIALIZED));
    }

    public Violations appendAccountNotInitialized() {
        return new Violations(violations.append(ACCOUNT_NOT_INITIALIZED));
    }

    public static Violations accountWithCardNotActive() {
        return new Violations(List.of(CARD_NOT_ACTIVE));
    }

    public Violations appendAccountWithCardNotActive() {
        return new Violations(violations.append(CARD_NOT_ACTIVE));
    }

    public static Violations accountWithInsufficientLimits() {
        return new Violations(List.of(INSUFFICIENT_LIMIT));
    }

    public Violations appendAccountWithInsufficientLimits() {
        return new Violations(violations.append(INSUFFICIENT_LIMIT));
    }

    public static Violations accountWithHighFrequencySmallInterval() {
        return new Violations(List.of(HIGH_FREQUENCY_SMALL_INTERVAL));
    }

    public Violations appendAccountWithHighFrequencySmallInterval() {
        return new Violations(violations.append(HIGH_FREQUENCY_SMALL_INTERVAL));
    }

    public static Violations accountWithDoubleTransaction() {
        return new Violations(List.of(DOUBLE_TRANSACTION));
    }

    public Violations appendAccountWithDoubleTransaction() {
        return new Violations(violations.append(DOUBLE_TRANSACTION));
    }

    public boolean hasNotViolations() {
        return violations.size() == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Violations that = (Violations) o;
        return Objects.equals(violations, that.violations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(violations);
    }

    @Override
    public String toString() {
        return "Violations{" +
                "violations=" + violations +
                '}';
    }
}