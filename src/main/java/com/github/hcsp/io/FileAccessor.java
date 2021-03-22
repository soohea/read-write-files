package com.github.hcsp.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = fileInputStream.read()) != -1) {
                sb.append((char) c);
            }
            return Arrays.asList(sb.toString().split("\n"));
        }
    }

    public static List<String> readFile2(File file) throws IOException {
        try (FileReader fileReader = new FileReader(file)) {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = fileReader.read()) != -1) {
                sb.append((char) c);
            }
            return Arrays.asList(sb.toString().split("\n"));
        }
    }

    public static List<String> readFile3(File file) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        return Files.readAllLines(path);
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(String.join("\n", lines).getBytes());
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(String.join("\n", lines));
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        String content = String.join("\n", lines);
        Files.write(path, content.getBytes(), StandardOpenOption.CREATE);
    }

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("AAA", "BBB", "CCC");
        writeLinesToFile1(lines, testFile);
        writeLinesToFile2(lines, testFile);
        writeLinesToFile3(lines, testFile);

        System.out.println(readFile1(testFile));
        System.out.println(readFile2(testFile));
        System.out.println(readFile3(testFile));
    }
}
