package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.StandardOpenOption.WRITE;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        if (file != null) {
            byte[] readFileByBytes = new byte[(int)file.length()];
            InputStream inputFromFile = new FileInputStream(file);
            inputFromFile.read(readFileByBytes);
            String[] stringInFile = new String(readFileByBytes).split("\n");
            return Arrays.asList(stringInFile);
        }
        return null;
    }

    public static List<String> readFile2(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static List<String> readFile3(File file) throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream outputToFile = new FileOutputStream(file);
        for (String s : lines) {
            outputToFile.write(s.getBytes());
            outputToFile.write('\n');
        }
        outputToFile.close();
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines, WRITE);
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
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
