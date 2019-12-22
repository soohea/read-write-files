package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        List lines = FileUtils.readLines(file, "UTF-8");
        return lines;
    }

    public static List<String> readFile2(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        return lines;
    }

    public static List<String> readFile3(File file) throws IOException {
     List<String> lines = Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8);
     return lines;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines, true);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        StringBuilder output = new StringBuilder();
        for (String s: lines) {
            output.append(s).append("\n");
        }
        bufferedWriter.write(output.toString());
        bufferedWriter.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Files.write(Paths.get(file.getPath()), lines);
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
