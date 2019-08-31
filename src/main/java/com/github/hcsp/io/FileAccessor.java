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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        String stringList = FileUtils.readFileToString(file, "utf-8");
        List<String> list = Arrays.asList(stringList.split("\n"));
        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> result = new ArrayList<>();
        FileReader fr = new FileReader(file);
        BufferedReader r = new BufferedReader(fr);
        String line = null;
        line = r.readLine();
        while (line != null) {
            result.add(line);
            line = r.readLine();
        }
        r.close();
        return result;
    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static List<String> readFile4(File file) throws IOException {
        InputStream is = new FileInputStream(file.getAbsolutePath());
        StringBuilder sb = new StringBuilder();
        List<String> result = new ArrayList<>();
        while (true) {
            int i = is.read();
            if (i == -1) {
                break;
            }
            char c = (char) i;
            if (c == '\n') {
                String str = sb.toString();
                result.add(str);
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        is.close();
        return result;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter w = new BufferedWriter(fw);
        for (String line :
                lines) {
            w.write(line);
            w.newLine();
        }
        w.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }

    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
        OutputStream os = new FileOutputStream(file.getAbsolutePath());
        for (String str :
                lines) {
            for (int i = 0, n = str.length(); i < n; i++) {
                StringBuilder sb = new StringBuilder();
                char c = str.charAt(i);
                os.write((int) c);
            }
            os.write((int) '\n');
        }
        os.close();
    }

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("a", "b", " ", "   ", "c");
//        writeLinesToFile1(lines, testFile);
//        writeLinesToFile2(lines, testFile);
        writeLinesToFile3(lines, testFile);
//
//        System.out.println(readFile1(testFile));
//        System.out.println(readFile2(testFile));
        System.out.println(readFile3(testFile));
    }
}
