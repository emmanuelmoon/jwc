package com.jwc;

import com.jwc.processors.WCProcessor;

import picocli.CommandLine;

public class Main {

    public static void main(String[] args) {
        new CommandLine(new WCProcessor()).execute(args);
    }
}