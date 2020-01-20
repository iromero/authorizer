package com.nubank.service;

import org.apache.commons.io.FileUtils;

import java.io.IOException;

public class FileOperatorReader {
    private final String filePathName;

    public FileOperatorReader(String filePathName) {
        this.filePathName = filePathName;
    }

    public String read() throws IOException {
        String bankOperationJson = FileUtils.readFileToString(FileUtils.getFile(filePathName), "UTF-8");
        return bankOperationJson;
    }
}