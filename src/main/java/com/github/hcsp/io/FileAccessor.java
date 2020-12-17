package com.github.hcsp.io;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        List<String> stringList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = new FileInputStream(file);
        while (true) {
            int ch = inputStream.read();
            if (ch == -1) {
                break;
            } else if (ch != ('\n') && ch != ('\r')) {
                stringBuilder.append((char) ch);
            } else if (ch == '\n') {
                stringList.add(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }
        }
        return stringList;
    }

    public static List<String> readFile2(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.lines().collect(Collectors.toList());
    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) {
        try {
            OutputStream outputStream = new FileOutputStream(file);
            for (String line : lines) {
                line = line + '\n';
                outputStream.write(line.getBytes(StandardCharsets.UTF_8));
            }
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

        for (String line : lines) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        for (String line : lines) {
            Files.write(file.toPath(), (line + '\n').getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        }

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
