package com.nubank.visitor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nubank.Account;
import com.nubank.BankOperation;
import com.nubank.Transaction;
import io.vavr.gson.VavrGson;

public class BankOperationFromJsonBuilderFactory {

    private final Gson gson;

    public BankOperationFromJsonBuilderFactory() {
        GsonBuilder builder = new GsonBuilder();
        VavrGson.registerAll(builder);
        gson = builder.create();
    }

    public BankOperation buildObject(String json) {
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
}
