package com.jwc.processors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public class WCProcessor implements Runnable {
    @Option(names = "-c", description = "count bytes")
    boolean bytes;

    @Option(names = "-l", description = "count lines")
    boolean lines;

    @Option(names = "-w", description = "count lines")
    boolean words;

    @Option(names = "-m", description = "count characters")
    boolean chars;

    @Parameters(paramLabel = "FILE", description = "files to count words from")
    String fileName;

    public void run() {
        if (bytes) {
            System.out.println(countBytes() + " " + fileName);
        } else if (lines) {
            System.out.println(countLines() + " " + fileName);
        } else if (words) {
            System.out.println(countWords() + " " + fileName);
        } else if (chars) {
            System.out.println(countChars() + " " + fileName);
        }
    }

    private int countChars() {
        int total = 0;

        int lineSeparatorLength = System.lineSeparator().length();
        try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(Paths.get(fileName)))) {
            char[] buffer = new char[8192];
            int charsRead;

            while ((charsRead = reader.read(buffer)) != -1) {
                total += charsRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return total;
    }

    private int countWords() {
        int total = 0;
        try (FileInputStream fis = new FileInputStream(fileName)) {
            InputStreamReader isr = new InputStreamReader(fis, Charset.defaultCharset().displayName());
            BufferedReader reader = new BufferedReader(isr);

            String str;

            while ((str = reader.readLine()) != null) {
                str = str.trim();
                if (str.isEmpty()) {
                    continue;
                }
                String[] words = str.split("\\s+");

                total += words.length;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return total;
    }

    private int countLines() {
        int total = 0;

        try (FileInputStream fis = new FileInputStream(fileName)) {
            InputStreamReader isr = new InputStreamReader(fis, Charset.defaultCharset().displayName());
            BufferedReader reader = new BufferedReader(isr);

            while ((reader.readLine()) != null) {
                total += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return total;
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
