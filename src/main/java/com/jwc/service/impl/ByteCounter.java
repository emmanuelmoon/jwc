package main.java.com.jwc.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import main.java.com.jwc.service.Counter;

public class ByteCounter implements Counter {

    @Override
    public int count(File file) {
        int total = 0;
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

    @Override
    public int count() {
        int total = 0;
        byte[] buffer = new byte[8192];

        try (BufferedInputStream bis = new BufferedInputStream(System.in)) {
            int bytesRead;

            while ((bytesRead = bis.read(buffer)) != -1) {
                total += bytesRead;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return total;
    }

}
