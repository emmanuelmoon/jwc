package main.java.com.jwc.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("LineCounter Tests")
class LineCounterTest {

    private LineCounter lineCounter;

    @BeforeEach
    void setUp() {
        lineCounter = new LineCounter();
    }

    @Test
    @DisplayName("Should count single line in file")
    void testCountSingleLine(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("single_line.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello World");
        }

        int result = lineCounter.count(testFile);

        assertEquals(1, result);
    }

    @Test
    @DisplayName("Should count multiple lines in file")
    void testCountMultipleLines(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("multiple_lines.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Line 1\n");
            writer.write("Line 2\n");
            writer.write("Line 3\n");
            writer.write("Line 4\n");
            writer.write("Line 5");
        }

        int result = lineCounter.count(testFile);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Should count empty file as zero lines")
    void testCountEmptyFile(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("empty.txt").toFile();
        testFile.createNewFile();

        int result = lineCounter.count(testFile);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("Should handle file with blank lines")
    void testCountFileWithBlankLines(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("blank_lines.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Line 1\n");
            writer.write("\n");
            writer.write("Line 3\n");
            writer.write("\n");
            writer.write("Line 5");
        }

        int result = lineCounter.count(testFile);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Should handle large files")
    void testCountLargeFile(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("large_file.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            for (int i = 0; i < 10000; i++) {
                writer.write("Line " + i + "\n");
            }
        }

        int result = lineCounter.count(testFile);

        assertEquals(10000, result);
    }

    @Test
    @DisplayName("Should handle file with trailing newline")
    void testCountFileWithTrailingNewline(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("trailing_newline.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Line 1\n");
            writer.write("Line 2\n");
        }

        int result = lineCounter.count(testFile);

        assertEquals(2, result);
    }

    @Test
    @DisplayName("Should handle file without trailing newline")
    void testCountFileWithoutTrailingNewline(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("no_trailing_newline.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Line 1\n");
            writer.write("Line 2");
        }

        int result = lineCounter.count(testFile);

        assertEquals(2, result);
    }

    @Test
    @DisplayName("Should handle special characters")
    void testCountFileWithSpecialCharacters(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("special_chars.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Line with @#$%^&*()\n");
            writer.write("Line with émojis 😀\n");
            writer.write("Line with symbols ~!@#$");
        }

        int result = lineCounter.count(testFile);

        assertEquals(3, result);
    }
}
