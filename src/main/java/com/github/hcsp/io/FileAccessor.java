package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {

    public static List<String> readFile1(File file) throws IOException {
        String s = FileUtils.readFileToString(file, Charset.defaultCharset());
        return new ArrayList<String>(Arrays.asList(s.split(",")));
    }

    public static List<String> readFile2(File file) throws IOException {
        InputStream stream = new FileInputStream(file);
        StringBuilder sb = new StringBuilder();
        while (true) {
            int b = stream.read();
            if (b == -1) {
                break;
            }
            sb.append((char) b);
        }

        return new ArrayList<String>(Arrays.asList(sb.toString().split(",")));
    }

    public static List<String> readFile3(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String contentLine = bufferedReader.readLine();
        StringBuilder sb = new StringBuilder();
        if (contentLine != null) {
            sb.append(contentLine);
            contentLine = bufferedReader.readLine();
        }
        return new ArrayList<String>(Arrays.asList(sb.toString().split(",")));
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        String s = String.join(",", lines);
        FileUtils.writeStringToFile(file, s, Charset.defaultCharset());
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        String s = String.join(",", lines);
        FileOutputStream fout = new FileOutputStream(file);
        byte b[] = s.getBytes();
        fout.write(b);
        fout.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        String s = String.join(",", lines);
        BufferedWriter bw = new BufferedWriter(writer);
        bw.write(s);
        bw.close();
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
