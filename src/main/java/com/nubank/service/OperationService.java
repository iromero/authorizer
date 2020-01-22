package com.nubank.service;

import com.nubank.model.Violations;

/**
 * Interface for all the bank operations, i.e transactions, account operations.
 */
public interface OperationService {
    Violations evalOperation();
}
