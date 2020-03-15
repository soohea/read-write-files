package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        return FileUtils.readLines(file, "UTF-8");
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        StringBuilder sb = new StringBuilder();
        int b;
        while ((b = fis.read()) != -1) {
            if (b == '\n') {
                lines.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append((char) b);
            }
        }
        fis.close();
        return lines;
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        br.close();
        return lines;
    }

    public static List<String> readFile4(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        for (String line : lines) {
            fos.write(line.getBytes());
            fos.write('\n');
        }
        fos.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        for (String line : lines) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("AAA", "BBB", "CCC");
        writeLinesToFile1(lines, testFile);
        writeLinesToFile2(lines, testFile);
        writeLinesToFile3(lines, testFile);
        writeLinesToFile4(lines, testFile);

        System.out.println(readFile1(testFile));
        System.out.println(readFile2(testFile));
        System.out.println(readFile3(testFile));
        System.out.println(readFile4(testFile));
    }
}
