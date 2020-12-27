package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        List<String> result = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file)) {
            StringBuilder sb = new StringBuilder();
            int b;
            while ((b = fis.read()) != -1) {
                if (b != '\n') {
                    sb.append((char) b);
                } else {
                    result.add(sb.toString());
                    sb = new StringBuilder();
                }
            }
        }
        return result;
    }


    public static List<String> readFile3(File file) throws IOException {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                result.add(br.readLine());
            }
        }
        return result;
    }

    public static List<String> readFile4(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, Charset.defaultCharset().name(), lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            for (String line : lines) {
                fos.write(line.getBytes(Charset.defaultCharset()));
                String lineSeparator = System.getProperty("line.separator");
                fos.write(lineSeparator.getBytes());
            }
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        try (FileWriter writer = new FileWriter(file);
             BufferedWriter bw = new BufferedWriter(writer)) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
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
