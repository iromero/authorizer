package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Violations;

/**
 * Interface for all the business rules relate with the account operations.
 */
public interface AccountBusinessRule {
    Violations evalOperation(Bank bank);
}
