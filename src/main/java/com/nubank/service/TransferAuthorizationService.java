package com.nubank.service;

import com.nubank.businessrule.AccountNoInitializedTransferRule;
import com.nubank.businessrule.TransferBusinessRule;
import com.nubank.model.Bank;
import com.nubank.model.Transfer;
import com.nubank.model.Violations;
import io.vavr.collection.List;

public class TransferAuthorizationService implements OperationService {

    private final Bank bank;
    private final Transfer transferToBeApproved;
    private final List<TransferBusinessRule> businessRules;

    public TransferAuthorizationService(Bank bank, Transfer transferToBeApproved) {
        this.bank = bank;
        this.transferToBeApproved = transferToBeApproved;
        this.businessRules = buildBusinessRules();
    }

    private List<TransferBusinessRule> buildBusinessRules() {
        return List.of(
                new AccountNoInitializedTransferRule()
        );
    }


    @Override
    public Violations evalOperation() {
        Violations violations = new Violations();
        for (TransferBusinessRule rule : businessRules) {
            violations = violations.append(rule.evalOperation(bank, transferToBeApproved));
        }
        return violations;
    }
}
