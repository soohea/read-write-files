package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws Exception {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }

    public static List<String> readFile2(File file) throws Exception {
        try {
            return FileUtils.readLines(file, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static List<String> readFile3(File file) throws Exception {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void writeLinesToFile1(List<String> lines, File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines
            ) {
                bw.write(line);
                bw.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) {
        try {
            FileUtils.writeLines(file, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeLinesToFile3(List<String> lines, File file) {
        try {
            Files.write(file.toPath(), lines, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
