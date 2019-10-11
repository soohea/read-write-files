package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    public static List<String> readFile2(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        List<String> sList = new ArrayList<>();
        List<String> cList = new ArrayList<>();
        while (true) {
            int ch = inputStream.read();
            if (ch == -1) {
                break;
            } else if (ch == '\n') {
                sList.add(String.join("", cList));
                cList.clear();
            } else {
                cList.add(Character.toString((char) ch));
            }
        }
        return sList;
    }

    public static List<String> readFile3(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<String> list = new ArrayList<>();
        while (true) {
            String line = bufferedReader.readLine();
            if (line != null) {
                list.add(line);
            } else {
                bufferedReader.close();
                return list;
            }
        }
    }

    public static List<String> readFile4(File file) throws IOException {
        Path path = Paths.get(file.getPath());
        return Files.readAllLines(path);

    }

    // 使用第三方库，例如Apache Commons IO
    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    // 使用最原始的FileInputStream/FileOutputStream，一个字符一个字符的读取
    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        for (String s : lines) {
            for (char c : s.toCharArray()) {
                outputStream.write(c);
            }
            outputStream.write('\n');
        }
    }

    // 使用BufferReader/BufferedWriter，一行一行地读写文件
    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (String s : lines) {
            bufferedWriter.write(s + "\n");
        }
        bufferedWriter.close();
    }

    // 使用Java 7+的NIO引入的Files.readAllBytes()/Files.readAllLines()/Files.write()方法
    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
        Path path = Paths.get(file.getPath());
        Files.write(path, lines, Charset.defaultCharset());
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
