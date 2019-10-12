package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        //read by Apache Commons IO
        List<String> res = FileUtils.readLines(file, Charset.defaultCharset());
        return res;
    }

    public static List<String> readFile2(File file) throws IOException {
        //read by BufferedReader
        List<String> res = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String contentLine = bufferedReader.readLine();
        while (contentLine != null) {
            res.add(contentLine);
            contentLine = bufferedReader.readLine();
        }
        bufferedReader.close();
        return res;
    }

    public static List<String> readFile3(File file) throws IOException {
        //read by NIO
        List<String> res = Files.readAllLines(file.toPath());
        return res;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        //write by Apache Commons IO
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        //write by BufferedWriter
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (String e : lines) {
            bufferedWriter.write(e);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        //write by NIO
        Files.write(file.toPath(), lines, StandardOpenOption.WRITE);
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
