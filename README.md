# JWC - Java Word Count

A high-performance Java implementation of the Unix `wc` (word count) command. JWC is a command-line utility that counts bytes, lines, words, and characters in files.

## Features

- **Count bytes** (`-c`): Display the number of bytes in a file
- **Count lines** (`-l`): Display the number of lines in a file
- **Count words** (`-w`): Display the number of words in a file
- **Count characters** (`-m`): Display the number of characters in a file
- **Multiple options**: Combine multiple flags to get various metrics at once
- **Native executable**: Built with GraalVM for fast startup and execution
- **Standard input support**: Read from stdin when no file is specified

## Prerequisites

- **Java 26** or higher (for building)
- **Maven 3.6+** (for building)
- **GraalVM** (optional, for native image compilation)

## Building the Application

### Build JAR Package

```bash
mvn clean package
```

This generates a JAR file in the `target/` directory.

### Native Executable

The native executable is automatically compiled during the standard build process using GraalVM Native Image. It will be available in the `target/` directory after building and starts instantly with minimal memory footprint.

## Running the Application

### Using Maven

Run directly with Maven:

```bash
mvn exec:java -Dexec.mainClass="com.jwc.Main" -Dexec.args="[options] [file]"
```

### Using JAR

After building:

```bash
java -jar target/jwc-1.0-SNAPSHOT.jar [options] [file]
```

### Using Native Executable

If you compiled a native executable:

```bash
./target/jwc [options] [file]
```

## Usage

### Basic Syntax

```
jwc [OPTIONS] [FILE]
```

### Options

- `-c` : Count bytes
- `-l` : Count lines
- `-w` : Count words
- `-m` : Count characters

### Examples

**Count lines in a file:**
```bash
java -jar target/jwc-1.0-SNAPSHOT.jar -l file.txt
```

**Count words in a file:**
```bash
java -jar target/jwc-1.0-SNAPSHOT.jar -w file.txt
```

**Count bytes in a file:**
```bash
java -jar target/jwc-1.0-SNAPSHOT.jar -c file.txt
```

**Count characters in a file:**
```bash
java -jar target/jwc-1.0-SNAPSHOT.jar -m file.txt
```

**Get multiple metrics at once:**
```bash
java -jar target/jwc-1.0-SNAPSHOT.jar -l -w -c file.txt
```

**Count from stdin:**
```bash
cat file.txt | java -jar target/jwc-1.0-SNAPSHOT.jar -l
```

## Project Structure

```
jwc/
├── pom.xml                          # Maven configuration
├── README.md                        # This file
├── src/
│   ├── main/
│   │   ├── java/com/jwc/
│   │   │   ├── Main.java           # Application entry point
│   │   │   └── WCProcessor.java    # CLI command processor
│   │   │   └── processors/
│   │   │       └── WCProcessor.java
│   │   │   └── service/
│   │   │       ├── Counter.java     # Counter interface
│   │   │       └── impl/            # Counter implementations
│   │   │           ├── ByteCounter.java
│   │   │           ├── LineCounter.java
│   │   │           ├── WordCounter.java
│   │   │           └── CharacterCounter.java
│   │   └── resources/              # Resource files
│   └── test/
│       └── java/                   # Test classes
└── target/                         # Build output directory
```

## Technologies

- **Language**: Java 26
- **Build Tool**: Maven
- **CLI Framework**: PicoCLI 4.7.7
- **Native Compilation**: GraalVM Native Image

## License

This project is provided as-is for educational and development purposes.