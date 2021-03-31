package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    public static List<String> readFile2(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> list = new ArrayList<>();
        String string;
        while ((string = reader.readLine()) != null) {
            list.add(string);
        }
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, Charset.defaultCharset().name(), lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (String line : lines) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines, Charset.defaultCharset());
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
