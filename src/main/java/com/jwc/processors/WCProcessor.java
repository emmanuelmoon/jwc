package com.jwc.processors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public class WCProcessor implements Runnable {
    @Option(names = "-c", description = "count bytes")
    boolean bytes;

    @Parameters(paramLabel = "FILE", description = "files to count words from")
    String fileName;

    public void run() {
        if (bytes) {
            System.out.println(countBytes() + " " + fileName);
        }

    }

    private int countBytes() {
        int total = 0;
        File file = new File(fileName);
        byte[] buffer = new byte[8192];

        try (FileInputStream fis = new FileInputStream(file)) {
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                total += bytesRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return total;
    }

}
