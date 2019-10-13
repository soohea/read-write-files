package com.github.hcsp.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.Writer;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        //使用最原始的FileInputStream/FileOutputStream，一个字符一个字符的读取
        InputStream is = new FileInputStream(file);
        List<String> lines = new LinkedList<>();
        String str = "";
        int n = 0;
        while (n != -1) {
            n = is.read();
            if (n == 10) {
                lines.add(str);
                str = "";
            } else {
                str += (char) n;
            }
        }
        return lines;
    }

    public static List<String> readFile2(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lines = new LinkedList<>();
        String str = "";
        while ((str = br.readLine()) != null) {
            lines.add(str);
        }
        br.close();
        return lines;
    }

    public static List<String> readFile3(File file) throws IOException {
        return FileUtils.readLines(file, "UTF-8");
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        //使用最原始的FileInputStream/FileOutputStream，一个字符一个字符的读取
        OutputStream os = new FileOutputStream(file, true);
        for (String line : lines) {
            char[] chr = line.toCharArray();
            for (char element : chr) {
                os.write(element);
            }
            os.write((char) 10);
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        //使用BufferReader/BufferedWriter，一行一行地读写文件
        Writer fw = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fw);
        for (String line : lines) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        OutputStream os = FileUtils.openOutputStream(file, true);
        FileUtils.writeLines(file, lines, null, true);
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
