package com.github.hcsp.io;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        return IOUtils.readLines(stream, Charset.defaultCharset());
    }

    public static List<String> readFile2(File file) throws IOException {
        BufferedReader newstream = new BufferedReader(new FileReader(file));
        Object[] array = newstream.lines().toArray();
        List<String> list = new ArrayList<>();
        for (Object obj : array) {
            list.add((String) obj);
        }
        newstream.close();
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileOutputStream outstream = new FileOutputStream(file);
        IOUtils.writeLines(lines, "\r\n", outstream, Charset.defaultCharset());
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter stream = new BufferedWriter(new FileWriter(file));
        for (String s : lines) {
            stream.write(s);
            stream.newLine();
        }
        stream.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
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
