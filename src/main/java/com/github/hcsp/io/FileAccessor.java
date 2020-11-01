package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        // 使用 CommonIO 的 FileUtils 读取文件内容
        return FileUtils.readLines(file);
    }

    public static List<String> readFile2(File file) throws IOException {
        // 使用NIO的 Files 工具类
        return Files.readAllLines(file.toPath());
    }

    public static List<String> readFile3(File file) throws IOException {
        // 使用BufferedReader
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> results = new ArrayList<>();
        String nextLine;
        while ((nextLine = bufferedReader.readLine()) != null) {
            results.add(nextLine);
        }
        bufferedReader.close();
        fileReader.close();
        return results;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        // 使用 FilesUtils
        for (String line :
                lines) {
            FileUtils.write(file, line, true);
            FileUtils.write(file, "\n", true);
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        // 使用 NIO 的 Files.write()
        Files.write(file.toPath(), lines, StandardOpenOption.APPEND);
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        // 使用BufferedWriter输出
        FileWriter fileWriter = new FileWriter(file.toString());
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for (String line : lines) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
        // 重要，没有会输出不了
        bufferedWriter.close();
        fileWriter.close();
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
