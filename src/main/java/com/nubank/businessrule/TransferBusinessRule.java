package com.nubank.businessrule;

import com.nubank.model.Bank;
import com.nubank.model.Transfer;
import com.nubank.model.Violations;

public interface TransferBusinessRule {
    Violations evalOperation(Bank bank, Transfer transferToBeApproved);
}
