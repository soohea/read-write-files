package com.github.hcsp.io;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    //使用最原始的FileInputStream/FileOutputStream，一个字符一个字符的读取
    public static List<String> readFile1(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        return IOUtils.readLines(inputStream, Charset.defaultCharset());

        /*最原始方法，不需要依赖第三方库
        List<String> charList = new ArrayList<>();
        while (true) {
            int b = inputStream.read();
            if (b == -1) {
                break;
            }
            charList.add((String.valueOf(b)));
            System.out.println((char) b);

        }
        return charList;*/
    }

    //使用BufferReader/BufferedWriter，一行一行地读写文件
    public static List<String> readFile2(File file) throws IOException {
        List<String> listString = new ArrayList<>();
        FileReader fr = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fr);
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            listString.add(line);
        }
        bufferedReader.close();
        return listString;
    }

    //使用Java 7+的NIO引入的Files.readAllBytes()/Files.readAllLines()/Files.write()方法
    public static List<String> readFile3(File file) throws IOException {
        //JDK1.8以后可以省略第二个参数，默认是UTF-8编码
        List<String> lines = Files.readAllLines(Paths.get(String.valueOf(file)), StandardCharsets.UTF_8);
        return lines;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        IOUtils.writeLines(lines, "\n", outputStream, Charset.defaultCharset());
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();  //这个一定得要

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

