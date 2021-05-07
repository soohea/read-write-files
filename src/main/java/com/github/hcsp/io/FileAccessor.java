package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    // 【方法1】最原始的一个字节一个字节读取
    public static List<String> readFile1(File file) throws IOException {
        // 建立一个集合
        List<String> list = new ArrayList<>();
        // 创建InputStream
        InputStream is = new FileInputStream(file);
        // 暂存字节十进制编码
        int b;
        // 创建一个可变的字符串对象
        StringBuilder sb = new StringBuilder();
        // 读取字节相关的编码，当返回为-1代表遍历完啦
        while ((b = is.read()) != -1) {
            if (b == '\n') {
                // 向list中添加字符串
                list.add(sb.toString());
                // 当要换行时，清空StringBuilder
                sb.delete(0, sb.length());
            } else {
                // 不需要换行时就一直追加元素
                sb.append((char) b);
            }
        }
        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        // 建立一个集合
        List<String> list = new ArrayList<>();
        // 创建一个bufferedReader缓冲区
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String row;
        // 一行一行读取
        while ((row = bufferedReader.readLine()) != null) {
            list.add(row);
        }
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        // 一行一行依次读取文件中的内容
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        for (String line : lines) {
            os.write(line.getBytes());
            os.write('\n');
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        // 创建一个写入缓冲区
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        // 遍历
        for (String line : lines) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
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
