package com.github.hcsp.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileAccessor {


    public static List<String> readFile1(File file) throws Exception {
        List<String> result = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            int b;
            StringBuilder stringBuilder = new StringBuilder();
            while ((b = fileInputStream.read()) != -1) {
                if ('\n' != (char) b) {
                    stringBuilder.append((char) b);
                } else {
                    result.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
            }
        }
        return result;
    }

    public static List<String> readFile2(File file) throws Exception {
        return new BufferedReader(new FileReader(file)).lines().collect(Collectors.toList());
    }

    public static List<String> readFile3(File file) throws Exception {
        return Files.readAllLines(Paths.get(file.getPath()));
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws Exception {
        OutputStream fileOutputStream = new FileOutputStream(file);
        StringBuilder output = new StringBuilder();
        for (String line : lines) {
            output.append(line).append("\n");
        }
        fileOutputStream.write(output.toString().getBytes());
        fileOutputStream.close();

    }

    public static void writeLinesToFile2(List<String> lines, File file) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        StringBuilder output = new StringBuilder();
        for (String line : lines) {
            output.append(line).append("\n");
        }
        writer.write(output.toString());
        writer.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws Exception {
        Files.write(Paths.get(file.getPath()), lines);
    }

    public static void main(String[] args) throws Exception {
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
