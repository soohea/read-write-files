package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        return FileUtils.readLines(file, StandardCharsets.UTF_8);
    }

    public static List<String> readFile2(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> result = new ArrayList<>();
        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while (true) {
            line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            result.add(line);
        }
        bufferedReader.close();
        return result;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }

    public static void main(String[] args) {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("AAA", "BBB", "CCC");
        try {
            writeLinesToFile1(lines, testFile);
            writeLinesToFile2(lines, testFile);
            writeLinesToFile3(lines, testFile);
            System.out.println(readFile1(testFile));
            System.out.println(readFile2(testFile));
            System.out.println(readFile3(testFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
