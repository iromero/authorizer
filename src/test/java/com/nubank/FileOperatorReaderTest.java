package com.nubank;

import com.nubank.service.FileOperatorReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileOperatorReaderTest {

    @Test
    public void readAccountOperationFromFile() throws IOException {
        //given
        String path = "C:/Users/ayoro/Documents/Interviews/NuBank/Authorizer/src/main/resources/accountInputOne";

        //when
        FileOperatorReader reader = new FileOperatorReader(path);
        String jsonResult = reader.read();

        //then
        String jsonExpected = "{\"account\": {\"active-card\": true, \"available-limit\": 100}}";
        assertEquals(jsonExpected, jsonResult);
    }

}
