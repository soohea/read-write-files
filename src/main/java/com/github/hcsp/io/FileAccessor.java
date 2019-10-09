package com.github.hcsp.io;

import com.google.common.base.Joiner;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    // 使用 Apache Commons IO 读取文件
    public static List<String> readFile1(File file) throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    // 使用最原始的 FileInputStream 读取文件
    public static List<String> readFile2(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        List<String> lines = new ArrayList<>();
        List<Character> characters = new ArrayList<>();

        while (true) {
            int c = inputStream.read();
            if (c == -1) {
                break;
            } else if (c == '\n') {
                lines.add(Joiner.on("").join(characters));
                characters.clear();
            } else {
                characters.add((char) c);
            }
        }

        return lines;
    }

    // 使用 BufferedReader 一行一行地读取文件
    public static List<String> readFile3(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList<>();

        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                bufferedReader.close();
                return lines;
            } else {
                lines.add(line);
            }
        }
    }

    // 使用 NIO 的 Files 类读取文件
    public static List<String> readFile4(File file) throws IOException {
        return Files.readAllLines(file.toPath());

    }

    // 使用 Apache Commons IO 写入文件
    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    // 使用最原始的 FileOutputStream 写入文件
    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        for (String line : lines) {
            for (char c : line.toCharArray()) {
                outputStream.write(c);
            }
            outputStream.write('\n');
        }
    }

    // // 使用 BufferedWriter 一行一行地写入文件
    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (String line : lines) {
            bufferedWriter.write(line + "\n");
        }
        bufferedWriter.close();
    }

    // 使用 NIO 的 Files 类写入文件
    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
        String content = Joiner.on("\n").join(lines) + "\n";
        Files.write(file.toPath(), content.getBytes());
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
