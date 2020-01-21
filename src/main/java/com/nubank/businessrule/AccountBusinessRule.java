package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Violations;

public interface AccountBusinessRule {
    Violations evalOperation(Bank bank);
}
