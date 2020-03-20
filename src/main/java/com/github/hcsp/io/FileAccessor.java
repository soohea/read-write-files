package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        return FileUtils.readLines(file, "UTF-8");
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> content1 = Files.readAllLines(Paths.get(file.getPath()), StandardCharsets.UTF_8);
        return content1;
    }

    public static List<String> readFile3(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bf = new BufferedReader(fileReader);
        List<String> content2 = new ArrayList<String>();

        String s = bf.readLine();
        while (s != null) {
            content2.add(s);
            s = bf.readLine();
        }
        return content2;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        Files.write(Paths.get(file.getPath()), lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for (String s : lines) {
            bufferedWriter.write(s);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
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
