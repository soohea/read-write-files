package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1、使用第三方库，例如Apache Commons IO
 * 2、使用最原始的FileInputStream/FileOutputStream，一个字符一个字符的读取
 * 3、使用BufferReader/BufferedWriter，一行一行地读写文件
 * 4、使用Java 7+的NIO引入的Files.readAllBytes()/Files.readAllLines()/Files.write()方法
 */
public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        return FileUtils.readLines(file);
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(file);
        int b;
        StringBuilder sb = new StringBuilder();
        while ((b = fileInputStream.read()) != -1) {
            if (b == '\n' || b == '\r') {
                if (sb.length() != 0) {
                    lines.add(sb.toString());
                    sb.setLength(0);
                }
                continue;
            }
            sb.append((char) b);
        }
        return lines;
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    public static List<String> readFile4(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) {
        try {
            FileUtils.writeLines(file, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            for (String line : lines) {
                char[] chars = line.toCharArray();
                for (char c : chars) {
                    outputStream.write(c);
                }
                outputStream.write('\r');
                outputStream.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeLinesToFile3(List<String> lines, File file) {
        try (
                FileWriter fileWriter = new FileWriter(file);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesToFile4(List<String> lines, File file) {
        try {
            Files.createFile(file.toPath());
            Files.write(file.toPath(), lines, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("AAA", "BBB", "CCC");
        writeLinesToFile1(lines, testFile);
//        writeLinesToFile2(lines, testFile);
//        writeLinesToFile3(lines, testFile);
//        writeLinesToFile4(lines, testFile);

        System.out.println(readFile1(testFile));
//        System.out.println(readFile2(testFile));
//        System.out.println(readFile3(testFile));
//        System.out.println(readFile4(testFile));
    }
}
