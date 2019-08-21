package com.github.hcsp.io;


import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FileAccessor {
    //**通过第三方库 apache common io  读取文件**
    public static List<String> readFile1(File file) throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    //**通过Buffered 读取文件**
    public static List<String> readFile2(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> list = new ArrayList<>();
        String br;
        while ((br = reader.readLine()) != null) {
            list.add(br);
        }
        reader.close();
        return list;
    }

    //**通过NIO 读取文件**
    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(Paths.get(file.getPath()));
    }

    //**通过InputStream 读取文件**
    public static List<String> readFile4(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        List<String> reader = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        //先放置Buffer区 等一组参数读完再放进List中
        int get;
        while ((get = is.read()) != -1) {
            if (get == '\n') {
                reader.add(sb.toString());
                sb.delete(0, sb.length());
            } else {
                sb.append((char) get);
            }
        }
        return reader;
    }

    //**通过FileUtils 写入文件**
    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    //**通过buffered 写入文件**
    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (String txt : lines) {
            bufferedWriter.write(txt);
            //因为读取是一行一行读取，所以每写入一次就换行
            bufferedWriter.newLine();
        }
        //BufferedWriter使用时注意要关闭，否则可能出现数据还在缓冲区内，没有写进文件中
        bufferedWriter.close();
    }

    //**通过NIO 写入文件**
    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Files.write(Paths.get(file.getPath()), lines);
    }

    //**通过OutputStream 写入文件**
    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        for (String txt : lines) {
            os.write(txt.getBytes());
            os.write('\n');
            //通过换行符来分别写进的List一组参数
        }
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
