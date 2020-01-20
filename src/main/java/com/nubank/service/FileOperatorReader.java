package com.nubank.service;

import org.apache.commons.io.FileUtils;

import java.io.IOException;

public class FileOperatorReader {
    private final String filePathName;

    FileOperatorReader(String filePathName) {
        this.filePathName = filePathName;
    }

    String read() throws IOException {
        String bankOperationJson = FileUtils.readFileToString(FileUtils.getFile(filePathName), "UTF-8");
        return bankOperationJson;
    }
}