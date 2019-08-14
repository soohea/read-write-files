package com.github.hcsp.io;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        List<String> strList = new ArrayList<>();
        int read;
        StringBuffer stringBuffer = new StringBuffer();
        while ((read = is.read()) != -1) {
            stringBuffer.append((char) read);
        }
        String str = new String(stringBuffer);
        strList.addAll(Arrays.asList(str.split("\n")));
        return strList;
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> strList = FileUtils.readLines(file, "UTF-8");
        return strList;
    }

    public static List<String> readFile3(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> arrList = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            arrList.add(line);
        }
        return arrList;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        for (String line : lines) {
            os.write(line.getBytes()); // 一次写入一个元素中的所有char
            os.write('\n'); // 用单引号是char类型
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, "UTF-8", lines);
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for (String line : lines) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("a", "b", " ", "   ", "c");
        writeLinesToFile1(lines, testFile);
//        writeLinesToFile2(lines, testFile);
//        writeLinesToFile3(lines, testFile);

        System.out.println(readFile1(testFile));
//        System.out.println(readFile2(testFile));
//        System.out.println(readFile3(testFile));
    }
}
