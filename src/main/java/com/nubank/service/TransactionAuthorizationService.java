package com.nubank.service;

import com.nubank.businessrule.*;
import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;
import io.vavr.collection.List;

public class TransactionAuthorizationService implements OperationService {

    private final Bank bank;
    private final Transaction transactionToBeApproved;
    private final List<TransactionBusinessRule> businessRules;

    public TransactionAuthorizationService(Bank bank, Transaction transactionToBeApproved) {
        this.bank = bank;
        this.transactionToBeApproved = transactionToBeApproved;
        this.businessRules = buildBusinessRules();
    }

    public List<TransactionBusinessRule> buildBusinessRules() {
        List<TransactionBusinessRule> businessRuleList = List.of(
                new AccountNotInitializedRuleTransaction(),
                new AccountNoActiveRuleTransaction(),
                new InsufficientLimitsTransactionRuleTransaction(),
                new DoubleTransactionRuleTransaction(),
                new HighFrequencySmallIntervalTransactionRuleTransaction()
        );
        return businessRuleList;
    }

    @Override
    public Violations evalOperation() {
        Violations violations = new Violations();
        for (TransactionBusinessRule businessRule : businessRules) {
            violations = violations.append(businessRule.evalOperation(bank, transactionToBeApproved));
        }
        return violations;
    }
}