package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        List<String> list;
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = new FileInputStream(file);
        while (true) {
            int i = inputStream.read();
            if (i == -1) {
                String string = new String(stringBuffer);
                list = Arrays.asList(string.split("\r\n"));
                break;
            }
            char a = (char) i;
            stringBuffer.append(a);
        }
        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> list = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while (true) {
            line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            list.add(line);
        }
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }


    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        for (int i = 0; i < lines.size(); i++) {
            byte[] b = lines.get(i).getBytes();
            outputStream.write(b);
            if (i != lines.size() - 1) {
                outputStream.write("\r\n".getBytes());
            }
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (String line : lines) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
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
