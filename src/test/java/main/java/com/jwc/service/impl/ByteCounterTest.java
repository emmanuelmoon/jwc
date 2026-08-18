package main.java.com.jwc.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("ByteCounter Tests")
class ByteCounterTest {

    private ByteCounter byteCounter;

    @BeforeEach
    void setUp() {
        byteCounter = new ByteCounter();
    }

    @Test
    @DisplayName("Should count bytes in single line file")
    void testCountBytesInSingleLine(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("single_line.txt").toFile();
        String content = "Hello";
        Files.write(testFile.toPath(), content.getBytes(StandardCharsets.UTF_8));

        int result = byteCounter.count(testFile);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Should count bytes including newlines")
    void testCountBytesWithNewlines(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("newlines.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello\n");
            writer.write("World");
        }

        int result = byteCounter.count(testFile);

        // "Hello\nWorld" = 5 + 1 + 5 = 11 bytes
        assertEquals(11, result);
    }

    @Test
    @DisplayName("Should count zero bytes for empty file")
    void testCountEmptyFile(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("empty.txt").toFile();
        testFile.createNewFile();

        int result = byteCounter.count(testFile);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("Should handle ASCII characters")
    void testCountASCIICharacters(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("ascii.txt").toFile();
        String content = "abcdefghijklmnopqrstuvwxyz";
        Files.write(testFile.toPath(), content.getBytes(StandardCharsets.UTF_8));

        int result = byteCounter.count(testFile);

        assertEquals(26, result);
    }

    @Test
    @DisplayName("Should handle file with spaces and tabs")
    void testCountBytesWithWhitespace(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("whitespace.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello  World\tTest");
        }

        int result = byteCounter.count(testFile);

        // "Hello World\tTest" = 5 + 2 + 5 + 1 + 4 = 17 bytes
        assertEquals(17, result);
    }

    @Test
    @DisplayName("Should handle large files")
    void testCountLargeFile(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("large_file.txt").toFile();
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            content.append("This is line number ").append(i).append("\n");
        }
        Files.write(testFile.toPath(), content.toString().getBytes(StandardCharsets.UTF_8));

        int result = byteCounter.count(testFile);

        assertTrue(result > 0);
        assertEquals(content.toString().getBytes(StandardCharsets.UTF_8).length, result);
    }

    @Test
    @DisplayName("Should handle file with only newlines")
    void testCountBytesOnlyNewlines(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("newlines_only.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("\n\n\n\n\n");
        }

        int result = byteCounter.count(testFile);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Should handle punctuation and special characters")
    void testCountBytesWithSpecialChars(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("special_chars.txt").toFile();
        String content = "!@#$%^&*()_+-={}[]|:;\"'<>,.?/";
        Files.write(testFile.toPath(), content.getBytes(StandardCharsets.UTF_8));

        int result = byteCounter.count(testFile);

        assertEquals(content.getBytes(StandardCharsets.UTF_8).length, result);
    }

    @Test
    @DisplayName("Should handle digits")
    void testCountBytesWithDigits(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("digits.txt").toFile();
        String content = "0123456789";
        Files.write(testFile.toPath(), content.getBytes(StandardCharsets.UTF_8));

        int result = byteCounter.count(testFile);

        assertEquals(10, result);
    }

    @Test
    @DisplayName("Should handle file with mixed content")
    void testCountBytesMixedContent(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("mixed.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Java 26\n");
            writer.write("Word Count: 42\n");
            writer.write("Special: !@#");
        }

        int result = byteCounter.count(testFile);

        assertTrue(result > 0);
    }
}
