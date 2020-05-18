package com.github.hcsp.io;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                int value = fileInputStream.read();
                if (value == -1) {
                    break;
                }
                stringBuilder.append((char) value);
            }
            String[] lines = stringBuilder.toString().split("\n");
            return Arrays.asList(lines);
        }
    }

    public static List<String> readFile2(File file) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);) {
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                int value = bufferedInputStream.read();
                if (value == -1) {
                    break;
                }
                stringBuilder.append((char) value);
            }
            String[] lines = stringBuilder.toString().split("\n");
            return Arrays.asList(lines);
        }
    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file, false)) {
            for (String line :
                    lines) {
                line = line + "\n";
                byte[] byteArray = line.getBytes();
                fileOutputStream.write(byteArray);
            }
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file, false);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);) {
            for (String line :
                    lines) {
                line = line + "\n";
                byte[] byteArray = line.getBytes();
                bufferedOutputStream.write(byteArray);
            }
            bufferedOutputStream.flush();
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        List<StringBuilder> lists = new ArrayList<>();
        for (String line :
                lines) {
            lists.add(new StringBuilder(line));
        }
        Files.write(file.toPath(), lists);
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
