package main.java.com.jwc.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import main.java.com.jwc.service.Counter;

public class CharacterCounter implements Counter {

    @Override
    public int count(File file) {
        int total = 0;

        try (BufferedReader reader = new BufferedReader(Files.newBufferedReader(Paths.get(file.getPath())))) {
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

    @Override
    public int count() {
        int total = 0;
        try (InputStreamReader isr = new InputStreamReader(System.in, Charset.defaultCharset().displayName());) {
            BufferedReader reader = new BufferedReader(isr);

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
}
