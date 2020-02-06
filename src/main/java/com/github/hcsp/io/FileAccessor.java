package com.github.hcsp.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class FileAccessor {

    // 使用最原始的FileInputStream/FileOutputStream，一个字符一个字符的读取
    public static List<String> readFile1(File file) throws IOException {
        Path path = file.toPath();
        Stream<String> ss = Files.lines(path);
        Object[] objects = ss.toArray();
        String[] stringArray = Arrays.copyOf(objects, objects.length, String[].class);
        return Arrays.asList(stringArray);
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> stringList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String res;
        while ((res = bufferedReader.readLine()) != null) {
            stringList.add(res);
        }
        bufferedReader.close();
        return stringList;
    }

    public static List<String> readFile3(File file) throws IOException {
        return FileUtils.readLines(file, "utf-8");
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (String line : lines) {
            bufferedWriter.write(line + System.lineSeparator());
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
        writeLinesToFile2(lines, testFile);
        writeLinesToFile3(lines, testFile);

        List<String> a = readFile1(testFile);
        a.forEach(System.out::println);
        List<String> b = readFile2(testFile);
        b.forEach(System.out::println);
        List<String> c = readFile3(testFile);
        c.forEach(System.out::println);
    }
}
