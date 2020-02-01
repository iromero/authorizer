package com.nubank;

import com.nubank.model.Bank;
import com.nubank.service.ProcessInputOperationService;
import com.nubank.service.ProcessInputOperationResult;
import com.nubank.service.ProcessOutputOperationService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AuthorizerApplication {

    private static String STOP_CRITERIA = "stop";

    public static void main(String[] args) throws IOException {
        Bank bank = Bank.init();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String bankOperationJson = "";//Java Strings are immutable
        while (!"stop".equals(bankOperationJson)) {
            bankOperationJson = br.readLine();
            if ("stop".equals(bankOperationJson)) {
                break;
            }
            if (bankOperationJson != null) {
                ProcessInputOperationService processInputOperationService = new ProcessInputOperationService(bank, bankOperationJson);
                ProcessInputOperationResult result = processInputOperationService.process();
                if (result.hasNotViolations()) {
                    bank = bank.update(result.getOperationInfo());//Update the account available limit as well as the
                    // approved transaction list.
                }
                ProcessOutputOperationService processOutputOperationService = new ProcessOutputOperationService(
                        bank.getCurrentAccount(result.getOperationInfo().getAccountId()).get(), result.getViolations());
                System.out.println(processOutputOperationService.process());//Transform the object result to JSON
            }
        }

        br.close();
    }

}
