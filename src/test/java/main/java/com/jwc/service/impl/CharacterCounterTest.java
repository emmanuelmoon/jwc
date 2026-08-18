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

@DisplayName("CharacterCounter Tests")
class CharacterCounterTest {

    private CharacterCounter characterCounter;

    @BeforeEach
    void setUp() {
        characterCounter = new CharacterCounter();
    }

    @Test
    @DisplayName("Should count characters in single line")
    void testCountCharactersSingleLine(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("single_line.txt").toFile();
        String content = "Hello";
        Files.write(testFile.toPath(), content.getBytes(StandardCharsets.UTF_8));

        int result = characterCounter.count(testFile);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Should count characters including newlines")
    void testCountCharactersWithNewlines(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("newlines.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello\n");
            writer.write("World");
        }

        int result = characterCounter.count(testFile);

        // "Hello\nWorld" = 5 + 1 + 5 = 11 characters
        assertEquals(11, result);
    }

    @Test
    @DisplayName("Should count zero characters for empty file")
    void testCountEmptyFile(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("empty.txt").toFile();
        testFile.createNewFile();

        int result = characterCounter.count(testFile);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("Should count all ASCII characters")
    void testCountASCIICharacters(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("ascii.txt").toFile();
        String content = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Files.write(testFile.toPath(), content.getBytes(StandardCharsets.UTF_8));

        int result = characterCounter.count(testFile);

        assertEquals(52, result);
    }

    @Test
    @DisplayName("Should count spaces and tabs as characters")
    void testCountWhitespaceCharacters(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("whitespace.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello  World\tTest");
        }

        int result = characterCounter.count(testFile);

        // "Hello World\tTest" = 5 + 2 + 5 + 1 + 4 = 17 characters
        assertEquals(17, result);
    }

    @Test
    @DisplayName("Should handle file with only whitespace")
    void testCountOnlyWhitespace(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("whitespace_only.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("     ");
        }

        int result = characterCounter.count(testFile);

        assertEquals(5, result);
    }

    @Test
    @DisplayName("Should handle digits")
    void testCountDigits(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("digits.txt").toFile();
        String content = "0123456789";
        Files.write(testFile.toPath(), content.getBytes(StandardCharsets.UTF_8));

        int result = characterCounter.count(testFile);

        assertEquals(10, result);
    }

    @Test
    @DisplayName("Should handle special characters and punctuation")
    void testCountSpecialCharacters(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("special_chars.txt").toFile();
        String content = "!@#$%^&*()_+-={}[]|:;\"'<>,.?/";
        Files.write(testFile.toPath(), content.getBytes(StandardCharsets.UTF_8));

        int result = characterCounter.count(testFile);

        assertEquals(content.length(), result);
    }

    @Test
    @DisplayName("Should count file with multiple lines and mixed content")
    void testCountMultilineContent(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("multiline.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Line 1\n");
            writer.write("Line 2\n");
            writer.write("Line 3");
        }

        int result = characterCounter.count(testFile);

        // "Line 1\nLine 2\nLine 3" = 6 + 1 + 6 + 1 + 6 = 20 characters
        assertEquals(20, result);
    }

    @Test
    @DisplayName("Should handle large files")
    void testCountLargeFile(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("large_file.txt").toFile();
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            content.append("Character test line ").append(i).append("\n");
        }
        Files.write(testFile.toPath(), content.toString().getBytes(StandardCharsets.UTF_8));

        int result = characterCounter.count(testFile);

        assertEquals(content.toString().length(), result);
    }

    @Test
    @DisplayName("Should handle file with only newlines")
    void testCountOnlyNewlines(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("newlines_only.txt").toFile();
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("\n\n\n");
        }

        int result = characterCounter.count(testFile);

        assertEquals(3, result);
    }

    @Test
    @DisplayName("Should handle mixed ASCII content")
    void testCountMixedASCII(@TempDir Path tempDir) throws IOException {
        File testFile = tempDir.resolve("mixed_ascii.txt").toFile();
        String content = "The quick brown fox jumps over the lazy dog 123!@#";
        Files.write(testFile.toPath(), content.getBytes(StandardCharsets.UTF_8));

        int result = characterCounter.count(testFile);

        assertEquals(content.length(), result);
    }
}
