package com.github.hcsp.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;


public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        StringBuffer stringBuffer = new StringBuffer();
        int readInt = inputStream.read();
        while (readInt != -1) {
            stringBuffer.append((char) readInt);
            readInt = inputStream.read();
        }
        String intputStr = new String(stringBuffer);
        List<String> list = Arrays.asList(intputStr.split("\n"));

        inputStream.close();
        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> list = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(file.toPath())));
        String lineStr = bufferedReader.readLine();
        while (lineStr != null) {
            list.add(lineStr);
            lineStr = bufferedReader.readLine();
        }

        bufferedReader.close();
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        for (String string : lines) {
            byte b[] = string.getBytes();
            fileOutputStream.write(b);
            fileOutputStream.write('\n');
        }
        fileOutputStream.close();
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(String.valueOf(file.toPath())));
        String write = "";
        for (String string : lines) {
            write += string + "\n";
        }
        bufferedWriter.write(write);
        bufferedWriter.close();
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
