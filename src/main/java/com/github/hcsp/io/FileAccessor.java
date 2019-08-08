package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

//import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
//        List<String> list = new ArrayList<>();
//        list.add(FileUtils.readFileToString(file,Charset.defaultCharset()));
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    //
    public static List<String> readFile2(File file) throws IOException {
        List<String> list = new ArrayList<>();

        FileInputStream fis = new FileInputStream(file);
        StringBuilder s = new StringBuilder();
        int a;
        while ((a = fis.read()) != -1) {
            if ((char) a == '\n') {
                list.add(s.toString());
                s.delete(0, s.length());
            } else {
                s.append((char) a);
            }
        }
        return list;
    }

    //
    public static List<String> readFile3(File file) throws IOException {
//        FileWriter fw = new FileWriter(file);
//        BufferedWriter buffwrite = new BufferedWriter(fw);
//        for (String line:lines) {
//            buffwrite.write(line);
//        }
//        buffwrite.flush();
        List<String> list = new ArrayList<>();

        FileReader fr = new FileReader(file);
        BufferedReader buffread = new BufferedReader(fr);
        String line;
        while ((line = buffread.readLine()) != null) {
            list.add(line);
        }
        return list;
    }

    public static List<String> readFile4(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    //
    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
//        for (String line:lines) {
//            FileUtils.writeStringToFile(file,line, Charset.defaultCharset(),true);
//        }
        FileUtils.writeLines(file, lines);
    }

    //
    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileOutputStream os = new FileOutputStream(file);

        for (String line : lines) {
            byte[] bytesArray = line.getBytes();
            os.write(bytesArray);
            os.write('\n');
        }
    }

    //
    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter buffwrite = new BufferedWriter(fw);
        for (String line : lines) {
            buffwrite.write(line);
            buffwrite.write('\n');
        }
        buffwrite.flush();
    }

    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
//        for (String line:lines) {
//            Files.write(file.toPath(),line.getBytes(), StandardOpenOption.APPEND);
//        }
        Files.write(file.toPath(), lines);
    }

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("AAA", "BBB", "CCC");
//        writeLinesToFile1(lines, testFile);
//        writeLinesToFile2(lines, testFile);
//        writeLinesToFile3(lines, testFile);
        writeLinesToFile4(lines, testFile);
//
//        System.out.println(readFile1(testFile));
//        System.out.println(readFile2(testFile));
//        System.out.println(readFile3(testFile));
        System.out.println(readFile4(testFile));
    }
}
