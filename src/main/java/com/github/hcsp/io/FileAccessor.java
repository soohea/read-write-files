package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        //使用files包下的readAllLines方法读取所有数据
        return Files.readAllLines(file.toPath(), Charset.defaultCharset());
    }

    public static List<String> readFile2(File file) throws IOException {
        // 引用第三方库apache.commons.io.FileUtils
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    public static List<String> readFile3(File file) throws IOException {
        // 读取字符流
        List<String> read3 = new ArrayList<>();
        Reader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        while (true) {
            String abc = bufferedReader.readLine();
            if (abc == null) {
                break;
            }
            read3.add(abc);
        }
        return read3;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file));
        BufferedWriter shujuliu3 = new BufferedWriter(write);
        for (String everyone : lines) {
            shujuliu3.write(everyone);
            shujuliu3.newLine();
            shujuliu3.flush();
        }
        shujuliu3.close();
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
