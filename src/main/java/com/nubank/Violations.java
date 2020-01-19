package com.nubank;

import io.vavr.collection.List;

import java.util.Objects;

public class Violations {
    private final List<String> violations;

    public Violations(List<String> violations) {
        this.violations = violations;
    }

    public static Violations noViolations() {
        return new Violations(List.empty());
    }

    public static Violations accountAlreadyInitialized() {
        return new Violations(List.of("account-already-initialized"));
    }

    public static Violations accountNotInitialized() {
        return new Violations(List.of("account-not-initialized"));
    }

    public static Violations accountWithCardNotActive() {
        return new Violations(List.of("card-not-active"));
    }

    public static Violations accountWithInsufficientLimits() {
        return new Violations(List.of("insufficient-limit"));
    }

    public static Violations accountWithHighFrequencySmallInterval() {
        return new Violations(List.of("high-frequency-small-interval"));
    }

    public static Violations accountWithDoubleTransaction() {
        return new Violations(List.of("double-transaction"));
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
}