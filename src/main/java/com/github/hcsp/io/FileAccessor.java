package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FileAccessor {

    private static final int NEW_LINE = '\n';

    public static List<String> readFile1(File file) throws IOException {
        List<String> result = new LinkedList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] bytes = new byte[2048];
            int b, currentIndex = 0;
            while ((b = fileInputStream.read()) != -1) {
                if (NEW_LINE == b) {
                    result.add(new String(bytes, 0, currentIndex));
                    currentIndex = 0;
                    continue;
                }
                bytes[currentIndex++] = (byte) b;
            }
        }
        return result;
    }

    public static List<String> readFile2(File file) throws IOException {
        return FileUtils.readLines(file);
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> result = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }
        }
        return result;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            for (String line : lines) {
                for (byte b : line.getBytes()) {
                    fileOutputStream.write(b);
                }
                fileOutputStream.write(NEW_LINE);
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
