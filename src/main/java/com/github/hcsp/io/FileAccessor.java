package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static List<String> readFile2(File file) throws IOException {
        return FileUtils.readLines(file, "UTF-8");

    }

    public static List<String> readFile3(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<String> list = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {

            list.add(line);
        }
        bufferedReader.close();
        return list;

    }

    public static Path writeLinesToFile1(List<String> lines, File file) throws IOException {
        return Files.write(file.toPath(), lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines, true);
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
        for (String line : lines) {
            bWriter.write(line);
            bWriter.newLine();
        }
        bWriter.close();
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
