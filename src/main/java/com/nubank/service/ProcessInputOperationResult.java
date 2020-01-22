package com.nubank.service;

import com.nubank.model.OperationInfo;
import com.nubank.model.Violations;

/**
 * Contains the result of an bank operation input processing.
 */
public class ProcessInputOperationResult {
    private final Violations violations;
    private final OperationInfo operationInfo;

    public ProcessInputOperationResult(Violations violations, OperationInfo operationInfo) {
        this.violations = violations;
        this.operationInfo = operationInfo;
    }

    public boolean hasNotViolations() {
        return violations.hasNotViolations();
    }

    public Violations getViolations() {
        return violations;
    }

    public OperationInfo getOperationInfo() {
        return operationInfo;
    }
}