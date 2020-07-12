package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        //磁盘到内存，InputStream read
        InputStream in = new FileInputStream(file);
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        while (true) {
            int data = in.read();
            if (data == -1) {
                break;
            }
            if (data != 10) {
                sb.append((char) data);
            } else {
                result.add(sb.toString());
                sb.delete(0, sb.length());
            }
        }
        return result;
    }

    public static List<String> readFile2(File file) throws IOException {
        return FileUtils.readLines(file);
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> list = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
        }
        return list;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        //内存到磁盘，OutputStream write
        OutputStream out = new FileOutputStream(file);
        for (int i = 0; i < lines.size(); i++) {
            byte[] byteArray = lines.get(i).getBytes();
            for (byte b : byteArray) {
                out.write(b);
            }
            out.write('\n');
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (String str : lines) {
            bufferedWriter.write(str);
            bufferedWriter.write("\n");
            bufferedWriter.flush();
        }
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
