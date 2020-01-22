package com.nubank;

import com.nubank.model.Bank;
import com.nubank.service.ProcessInputOperationService;
import com.nubank.service.ProcessInputOperationResult;
import com.nubank.service.ProcessOutputOperationService;

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
                ProcessInputOperationService processInputOperationService = new ProcessInputOperationService(bank, bankOperationJson);
                ProcessInputOperationResult result = processInputOperationService.process();
                if (result.hasNotViolations()) {
                    bank = bank.update(result.getOperationInfo());
                }
                ProcessOutputOperationService processOutputOperationService = new ProcessOutputOperationService(bank.getCurrentAccount(), result.getViolations());
                System.out.println(processOutputOperationService.process());
            }
        }

        br.close();
    }

}
