package com.jwc.processors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

import main.java.com.jwc.service.Counter;
import main.java.com.jwc.service.impl.ByteCounter;
import main.java.com.jwc.service.impl.CharacterCounter;
import main.java.com.jwc.service.impl.LineCounter;
import main.java.com.jwc.service.impl.WordCounter;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

public class WCProcessor implements Runnable {

	private static final Counter characterCounter = new CharacterCounter();
	private static final Counter wordCounter = new WordCounter();
	private static final Counter byteCounter = new ByteCounter();
	private static final Counter lineCounter = new LineCounter();

	@Option(names = "-c", description = "count bytes")
	boolean bytes;

	@Option(names = "-l", description = "count lines")
	boolean lines;

	@Option(names = "-w", description = "count words")
	boolean words;

	@Option(names = "-m", description = "count characters")
	boolean chars;

	@Parameters(paramLabel = "FILE", arity = "0..1", description = "files to count words from")
	private File inputFile;

	public void run() {
		List<Counter> counters = new ArrayList<>();
		if (bytes)
			counters.add(byteCounter);
		if (lines)
			counters.add(lineCounter);
		if (words)
			counters.add(wordCounter);
		if (chars)
			counters.add(characterCounter);

		if (counters.isEmpty()) {
			counters.add(lineCounter);
			counters.add(wordCounter);
			counters.add(byteCounter);
		}

		for (Counter counter : counters) {
			if (inputFile == null) {
				System.out.print(counter.count() + " ");
			} else {
				System.out.print(counter.count(inputFile) + " ");
			}
		}

		if (Objects.nonNull(inputFile)) {
			System.out.println(inputFile.getName());
		}
	}

}
