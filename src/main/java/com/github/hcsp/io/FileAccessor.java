package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileAccessor {
    //原始的FileInputStream，一个字符一个字符的读取
    public static List<String> readFile1(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        List<String> list = new ArrayList<>();

        while (true) {
            int a = is.read();
            if (a == -1) {
                break;
            }
            list.add(String.valueOf((char) a));
        }
        is.close();
        return list;
    }

    //使用BufferReader，一行一行地读文件
    public static List<String> readFile2(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader bufr = new BufferedReader(fr);
        List<String> list = new ArrayList<>();
        String a = "";
        while ((a = bufr.readLine()) != null) {
            list.add(a);
        }
        bufr.close();
        fr.close();
        return list;
    }

    //使用Java 7+的NIO引入的Files.readAllBytes()/Files.readAllLines()方法
    public static List<String> readFile3(File file) throws IOException {
        List<String> list = new ArrayList<>();
        return Files.readAllLines(file.toPath());
    }

    //使用第三方库，Apache Commons IO
    public static List<String> readFile4(File file) throws IOException {
        List<String> list = new ArrayList<>();
        return list = Collections.singletonList(FileUtils.readFileToString(file));
    }

    //原始的FileOutputStream，一个字符一个字符的读取
    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        String s = "";
        for (String a : lines) {
            s += a;
        }
        byte[] bytes = s.getBytes();
        fos.write(bytes);
        fos.close();
    }

    //使用BufferWriter，一行一行地读文件
    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bufw = new BufferedWriter(fw);
        for (int i = 0; i < lines.size(); i++) {
            bufw.write(lines.get(i));
            bufw.newLine();
        }
        bufw.close();
        fw.close();
    }

    //使用Java 7+的NIO引入的Files.write()方法
    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        String s = "";
        for (String a : lines) {
            s += a;
        }
        Files.write(file.toPath(), s.getBytes());

    }

    // 使用第三方库，Apache Commons IO
    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
        String s = "";
        for (String a : lines) {
            s += a;
        }
        FileUtils.writeStringToFile(file, s, "GBK");

    }

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("AAA", "BBB", "CCC");
        writeLinesToFile1(lines, testFile);
        System.out.println(readFile1(testFile));

        writeLinesToFile2(lines, testFile);
        System.out.println(readFile2(testFile));

        writeLinesToFile3(lines, testFile);
        System.out.println(readFile3(testFile));

        writeLinesToFile4(lines, testFile);
        System.out.println(readFile4(testFile));


    }
}
