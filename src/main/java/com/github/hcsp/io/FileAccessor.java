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
        return IOUtils.readLines(new FileInputStream(file), Charset.defaultCharset());
    }

    public static List<String> readFile2(File file) throws IOException {
        String line;
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> list = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
        }
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(new File(String.valueOf(file)).toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        IOUtils.writeLines(lines, "\r\n", outputStream, Charset.defaultCharset());
    }


    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (String str : lines) {
            bufferedWriter.write(str);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("AAA", "BBB", "CCC");
        writeLinesToFile1(lines, testFile);
        System.out.println(readFile1(testFile));

        writeLinesToFile2(lines, testFile);
        System.out.println(readFile2(testFile));

        writeLinesToFile3(lines, testFile);
        System.out.println(readFile3(testFile));
    }
}
