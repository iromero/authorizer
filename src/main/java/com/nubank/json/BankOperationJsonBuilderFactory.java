package com.nubank.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nubank.model.Transfer;
import com.nubank.operation.AccountOperation;
import com.nubank.operation.BankOperation;
import com.nubank.operation.TransactionOperation;
import com.nubank.operation.TransferOperation;
import io.vavr.gson.VavrGson;

import java.time.LocalDateTime;

/**
 * Factory to serialized/deserialize an account or transaction operation to/from json.
 */
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
        if (json.startsWith("{\"transfer")) {
            return buildTransfer(json);
        }
        return buildAccount(json);
    }

    private BankOperation buildTransfer(String json) {
        return gson.fromJson(json, TransferOperation.class);
    }

    AccountOperation buildAccount(String json) {
        return gson.fromJson(json, AccountOperation.class);
    }

    TransactionOperation buildTransaction(String json) {
        return gson.fromJson(json, TransactionOperation.class);
    }

    /**
     * Convert a Transaction or an Account operation object to Json
     *
     * @param bankOperation A transaction or an account operation object
     * @return The Json String for a transaction or an account operation.
     */
    public String toJson(BankOperation bankOperation) {
        return bankOperation.toJson(gson);
    }
}