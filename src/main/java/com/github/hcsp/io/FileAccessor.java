package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        isExistence(file);
        return FileUtils.readLines(file, "utf-8");
    }

    public static List<String> readFile2(File file) throws IOException {
        isExistence(file);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String contentLine = br.readLine();
        List<String> list = new ArrayList<>();
        while (contentLine != null) {
            list.add(contentLine);
            contentLine = br.readLine();
        }
        br.close();
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        isExistence(file);
        Path path = Paths.get(file.getAbsolutePath());
        return Files.readAllLines(path);
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, "UTF-8", lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter bf = new BufferedWriter(new FileWriter(file));
        for (String line : lines) {
            bf.write(line + System.lineSeparator());
        }
        bf.flush();
        bf.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Path path = Paths.get(file.getAbsolutePath());
        Files.write(path, lines, StandardCharsets.UTF_8);
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

    public static void isExistence(File file) {
        if (!file.exists()) {
            throw new RuntimeException("要读取的文件不存在");
        }
    }
}
