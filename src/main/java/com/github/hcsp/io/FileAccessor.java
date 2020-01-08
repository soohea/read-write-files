package com.github.hcsp.io;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import jdk.management.resource.internal.inst.FileInputStreamRMHooks;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1];
        StringBuilder src = new StringBuilder();
        while (fileInputStream.read(bytes) != -1) {
            src.append(new String(bytes));
        }
        String[] split = src.toString().split("\n");
        fileInputStream.close();
        return Arrays.asList(split);

    }

    public static List<String> readFile2(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        List<String> readResult = IOUtils.readLines(fileInputStream);
        fileInputStream.close();
        return readResult;
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> readResult = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String line = bufferedReader.readLine();
        while (line != null) {
            readResult.add(line);
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        return readResult;

    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        for (String s : lines) {
            fileOutputStream.write(s.getBytes());
            fileOutputStream.write("\n".getBytes());
        }
        fileOutputStream.close();
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        IOUtils.writeLines(lines, "\n", fileOutputStream);
        fileOutputStream.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        for (String s : lines) {
            bufferedWriter.write(s);
            bufferedWriter.write("\n");
        }
        bufferedWriter.flush();
        bufferedWriter.close();
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
