package com.nubank.service;

import com.nubank.businessrule.BusinessRule;
import com.nubank.businessrule.DoubleTransactionRule;
import com.nubank.businessrule.HighFrequencySmallIntervalTransactionRule;
import com.nubank.businessrule.InsuficientLimitsTransactionRule;
import com.nubank.model.Account;
import com.nubank.model.Bank;
import com.nubank.model.Transaction;
import com.nubank.model.Violations;
import io.vavr.collection.List;

public class TransactionAuthorizationService implements OperationService {

    private final Bank bank;
    private final Transaction transactionToBeApproved;
    private final List<BusinessRule> businessRules;

    public TransactionAuthorizationService(Bank bank, Transaction transactionToBeApproved) {
        this.bank = bank;
        this.transactionToBeApproved = transactionToBeApproved;
        businessRules = buildBusinessRule();
    }

    public List<BusinessRule> buildBusinessRule() {
        List<BusinessRule> businessRuleList = List.of(
                new InsuficientLimitsTransactionRule(),
                new DoubleTransactionRule(),
                new HighFrequencySmallIntervalTransactionRule()
        );
        return businessRuleList;
    }

    @Override
    public Violations evalOperation() {
        Violations violations = new Violations();
        if (!bank.existAccount()) {
            return violations.appendAccountNotInitialized();
        }
        Account currentAccount = bank.getCurrentAccount();
        if (currentAccount.isNotActive()) {
            return violations.appendAccountWithCardNotActive();
        }
        for (BusinessRule businessRule : businessRules) {
            violations = violations.append(businessRule.evalOperation(bank, transactionToBeApproved));
        }
        return violations;
    }


}