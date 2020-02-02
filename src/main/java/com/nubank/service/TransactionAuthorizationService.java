package com.nubank.service;

import com.nubank.businessrule.*;
import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;
import io.vavr.collection.List;

/**
 * Service that use a list of business rules to validate a transaction operation.
 */
public class TransactionAuthorizationService implements OperationService {

    private final Bank bank;
    private final Transaction transactionToBeApproved;
    private final List<TransactionBusinessRule> businessRules;

    public TransactionAuthorizationService(Bank bank, Transaction transactionToBeApproved) {
        this.bank = bank;
        this.transactionToBeApproved = transactionToBeApproved;
        this.businessRules = buildBusinessRules();
    }

    /**
     * Build a list of business rules to validate a transaction operation.
     *
     * @return a list of business rules to validate a transaction.
     */
    public List<TransactionBusinessRule> buildBusinessRules() {
        List<TransactionBusinessRule> businessRuleList = List.of(
                new AccountNotInitializedTransactionRule(),
                new AccountNoActiveTransactionRule(),
                new InsufficientLimitsTransactionTransactionRule(),
                new DoubleTransactionTransactionRule(),
                new HighFrequencySmallIntervalTransactionTransactionRule(),
                new MaxAmountExpendTransactionRule()
        );
        return businessRuleList;
    }

    /**
     * Validate a transaction operation taking into account a list of business rules.
     *
     * @return Violations if the transaction does not approve all the business rules otherwise it returns no violations.
     */
    @Override
    public Violations evalOperation() {
        Violations violations = new Violations();
        for (TransactionBusinessRule businessRule : businessRules) {
            violations = violations.append(businessRule.evalOperation(bank, transactionToBeApproved));
        }
        return violations;
    }
}