package com.github.hcsp.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.io.*;


public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        //从文件中读出字节流，直接读会原封不动地读出所有内容，去掉前后括号后按逗号分割重新转换成字符串集合
        //read()方法会读出当前流中下一个字节,并返回字节表示的int值（0~255)，下一个为空时返回-1
        //Arrays.ArrayList 是工具类 Arrays 的一个内部静态类，它没有完全实现List的方法，而 ArrayList直接实现了List 接口，实现了List所有方法。
        //二者的不同在于：Arrays.ArrayList将外部数组的引用直接通过“=”赋予内部的泛型数组，所以本质指向同一个数组。
        //ArrayList是将其他集合转为数组后copy到自己内部的数组的。
        StringBuffer sb = new StringBuffer();
        try (InputStream input = new FileInputStream(file)) {
            int n;
            while ((n = input.read()) != -1) {
                sb.append((char) n);
            }
        }
        String[] s = sb.toString().replace("[", "").replace("]", "").split(", ");
        List<String> list = Arrays.asList(s);
        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        BufferedReader buf = new BufferedReader(new FileReader(file));
        String str = null;
        StringBuffer sb = new StringBuffer();
        while ((str = buf.readLine()) != null) {
            sb.append(str);
        }
        buf.close();
        String[] s = sb.toString().replace("[", "").replace("]", "").split(", ");
        List<String> list = Arrays.asList(s);
        return list;

    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }


    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream f = new FileOutputStream(file);
        f.write(lines.toString().getBytes());
        f.close();
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(lines.toString());
        bw.close();
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
