package com.github.hcsp.io;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        int b;
        String s = "";
        while ((b = inputStream.read()) != -1) {
            s += (char) b;
        }
        //分割字符串
        String[] strings = s.replace("[", "").replace("]", "").split(", ");
        List<String> list = Arrays.asList(strings);
        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        //字符流
        Reader reader = new FileReader(file);
        int b;
        String s = "";
        while ((b = reader.read()) != -1) {
            s += (char) b;
        }
        reader.close();
        //分割字符串
        String[] strings = s.replace("[", "").replace("]", "").split(", ");
        List<String> list = Arrays.asList(strings);
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        //缓冲字节流
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        int b;
        String s = "";
        while ((b = bis.read()) != -1) {
            s += (char) b;
        }
        bis.close();
        //切割字符串
        String[] strings = s.replace("[", "").replace("]", "").split(", ");
        List<String> list = Arrays.asList(strings);
        return list;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        //字节流
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(lines.toString().getBytes());
        outputStream.close();
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        //字符流
        Writer writer = new FileWriter(file);
        writer.write(lines.toString());
        writer.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        //缓冲字节流
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bos.write(lines.toString().getBytes());
        bos.close();
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

