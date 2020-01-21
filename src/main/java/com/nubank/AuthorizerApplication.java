package com.nubank;

import com.nubank.model.Bank;
import com.nubank.service.ProcessInputOperation;
import com.nubank.service.ProcessInputOperationResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AuthorizerApplication {
    public static void main(String[] args) throws IOException {
        Bank bank = Bank.init();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String bankOperationJson = "";
        while (!"stop".equals(bankOperationJson)) {
            bankOperationJson = br.readLine();
            if ("stop".equals(bankOperationJson)) {
                break;
            }
            if (bankOperationJson != null) {
                ProcessInputOperation processInputOperation = new ProcessInputOperation(bank, bankOperationJson);
                ProcessInputOperationResult result = processInputOperation.process();
                if (result.hasNotViolations()) {
                    bank = bank.update(result.getOperationInfo());
                }
                ProcessOutputOperation processOutputOperation = new ProcessOutputOperation(bank.getCurrentAccount(), result.getViolations());
                System.out.println(processOutputOperation.process());
            }
        }

        br.close();
    }

}
