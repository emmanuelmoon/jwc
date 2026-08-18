package main.java.com.jwc.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("WordCounter Tests")
class WordCounterTest {

    private WordCounter wordCounter;

    @BeforeEach
    void setUp() {
        wordCounter = new WordCounter();
    }

    @Test
    @DisplayName("Should count single word")
    void testCountSingleWord(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("single_word.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello");
        }

        int result = wordCounter.count(testFile);

        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should count multiple words on single line")
    void testCountMultipleWordsOneLine(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("multiple_words.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello World from Java");
        }

        int result = wordCounter.count(testFile);

        assertEquals(4, result);
    }

    @Test
    @DisplayName("Should count words across multiple lines")
    void testCountWordsMultipleLines(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("multiple_lines.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello World\n");
            writer.write("This is Java\n");
            writer.write("Programming Language");
        }

        int result = wordCounter.count(testFile);

        assertEquals(7, result);
    }

    @Test
    @DisplayName("Should count words with various whitespace")
    void testCountWordsVariousWhitespace(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("whitespace.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Word1   Word2\t\tWord3\n");
            writer.write("   Word4  Word5   ");
        }

        int result = wordCounter.count(testFile);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Should handle empty file")
    void testCountEmptyFile(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("empty.txt").toFile();
        testFile.createNewFile();

        int result = wordCounter.count(testFile);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("Should ignore blank lines")
    void testCountIgnoreBlankLines(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("blank_lines.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Word1 Word2\n");
            writer.write("\n");
            writer.write("Word3 Word4\n");
            writer.write("   \n");
            writer.write("Word5");
        }

        int result = wordCounter.count(testFile);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Should count words with punctuation")
    void testCountWordsWithPunctuation(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("punctuation.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello, world! How are you?");
        }

        int result = wordCounter.count(testFile);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Should handle file with tabs and newlines")
    void testCountWordsWithTabsAndNewlines(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("tabs_newlines.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Word1\tWord2\tWord3\n");
            writer.write("Word4\t\tWord5");
        }

        int result = wordCounter.count(testFile);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Should handle large word count")
    void testCountLargeWordFile(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("large_words.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            for (int i = 0; i < 1000; i++) {
                writer.write("word1 word2 word3 word4 word5\n");
            }
        }

        int result = wordCounter.count(testFile);

        assertEquals(5000, result);
    }

    @Test
    @DisplayName("Should handle file with trailing whitespace")
    void testCountWordsTrailingWhitespace(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("trailing_ws.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("   Word1 Word2 Word3   ");
        }

        int result = wordCounter.count(testFile);

        assertEquals(3, result);
    }
}
