package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    //单个字符读取
    public static List<String> readFile1(File file) throws IOException {
        List<String> list = new ArrayList<>();
        InputStream inputStream = new FileInputStream(file);
        StringBuilder stringBuilder = new StringBuilder();
        int inputStreamToInteger;
        while ((inputStreamToInteger = inputStream.read()) != -1) {
            if ((char) inputStreamToInteger == '\n') {
                list.add(stringBuilder.toString());
                stringBuilder.delete(0, stringBuilder.length());
            } else {
                stringBuilder.append((char) inputStreamToInteger);
            }
        }
        return list;
    }


    //使用Files工具方法中的readAllLines方法
    public static List<String> readFile2(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    //使用BufferedReader整行读取
    public static List<String> readFile3(File file) throws IOException {
        List<String> readFileToList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            readFileToList.add(s);
        }
        return readFileToList;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    //使用FileWriter类中的write方法写入
    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }

    //使用PrintWriter中的write方法
    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (String element : lines
        ) {
            bufferedWriter.write(element);
            bufferedWriter.write("\n");
        }
        bufferedWriter.close();
    }

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("a", "b", " ", "   ", "c");
        writeLinesToFile1(lines, testFile);
        writeLinesToFile2(lines, testFile);
        writeLinesToFile3(lines, testFile);

        System.out.println(readFile1(testFile));
        System.out.println(readFile2(testFile));
        System.out.println(readFile3(testFile));
    }
}
