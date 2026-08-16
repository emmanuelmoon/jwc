package main.java.com.jwc.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.File;

import main.java.com.jwc.service.Counter;

public class WordCounter implements Counter {

    @Override
    public int count(File file) {
        int total = 0;
        try (FileInputStream fis = new FileInputStream(file)) {
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

    @Override
    public int count() {
        int total = 0;
        try (InputStreamReader isr = new InputStreamReader(System.in, Charset.defaultCharset().displayName());) {
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
}
