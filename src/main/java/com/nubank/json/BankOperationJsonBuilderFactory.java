package com.nubank.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nubank.AccountOperation;
import com.nubank.BankOperation;
import com.nubank.TransactionOperation;
import io.vavr.gson.VavrGson;

import java.time.LocalDateTime;

public class BankOperationJsonBuilderFactory {

    private final Gson gson;

    public BankOperationJsonBuilderFactory() {
        GsonBuilder builder = new GsonBuilder();
        VavrGson.registerAll(builder);
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gson = builder.create();
    }

    public BankOperation fromJson(String json) {
        if (json.startsWith("{\"transaction")) {
            return buildTransaction(json);
        }
        return buildAccount(json);
    }

    AccountOperation buildAccount(String json) {
        return gson.fromJson(json, AccountOperation.class);
    }

    TransactionOperation buildTransaction(String json) {
        return gson.fromJson(json, TransactionOperation.class);
    }

    public String toJson(BankOperation bankOperation) {
        if (bankOperation instanceof TransactionOperation) {
            return buildTransactionJson((TransactionOperation) bankOperation);
        } else {
            return buildAccountJson((AccountOperation) bankOperation);
        }
    }

    String buildAccountJson(AccountOperation account) {
        return gson.toJson(account);
    }

    String buildTransactionJson(TransactionOperation transaction) {
        return gson.toJson(transaction);
    }
}