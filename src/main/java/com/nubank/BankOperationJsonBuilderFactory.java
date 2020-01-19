package com.nubank;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nubank.Account;
import com.nubank.BankOperation;
import com.nubank.Transaction;
import io.vavr.gson.VavrGson;

public class BankOperationJsonBuilderFactory {

    private final Gson gson;

    public BankOperationJsonBuilderFactory() {
        GsonBuilder builder = new GsonBuilder();
        VavrGson.registerAll(builder);
        gson = builder.create();
    }

    public BankOperation fromJson(String json) {
        if (json.startsWith("{\"transaction")) {
            return buildTransaction(json);
        }
        return buildAccount(json);
    }

    Account buildAccount(String json) {
        return gson.fromJson(json, Account.class);
    }

    Transaction buildTransaction(String json) {
        return gson.fromJson(json, Transaction.class);
    }

    public String toJson(BankOperation bankOperation) {
        if (bankOperation instanceof Transaction) {
            return buildTransactionJson((Transaction) bankOperation);
        } else {
            return buildAccountJson((Account) bankOperation);
        }
    }

    String buildAccountJson(Account account) {
        return gson.toJson(account);
    }

    String buildTransactionJson(Transaction transaction) {
        return gson.toJson(transaction);
    }
}
