package com.github.hcsp.io;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<String> strList = new ArrayList<>();
        int ch = 0;
        try (FileInputStream in = new FileInputStream(file)) {
            while ((ch = in.read()) != -1) {
                if (ch == '\n' || ch == '\r') {
                    if (sb.length() != 0) {
                        strList.add(sb.toString());
                        sb.setLength(0);
                    }
                    continue;
                }
                sb.append((char) ch);
            }
        }
        return strList;
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> strList = new ArrayList<>();
        String line = null;
        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {
            while ((line = reader.readLine()) != null) {
                strList.add(line);
            }
        }
        return strList;
    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file)){
            for (String line : lines) {
                out.write(line.getBytes());
                out.write('\n');
            }
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
