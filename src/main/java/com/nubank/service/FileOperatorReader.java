package com.nubank.service;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.List;

public class FileOperatorReader {
    private final String filePathName;

    public FileOperatorReader(String filePathName) {
        this.filePathName = filePathName;
    }

    public io.vavr.collection.List<String> read() throws IOException {
        List<String> bankOperationJson = FileUtils.readLines(FileUtils.getFile(filePathName), "UTF-8");
        return io.vavr.collection.List.ofAll(bankOperationJson);
    }
}