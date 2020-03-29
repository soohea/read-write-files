package com.github.hcsp.io;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        List<String> list = IOUtils.readLines(in, Charset.defaultCharset());
        in.close();
        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        reader.close();
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> list = Files.readAllLines(Paths.get(String.valueOf(file)));
        return list;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        IOUtils.writeLines(lines, null, fileOutputStream, Charset.defaultCharset());
        fileOutputStream.close();
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Files.write(Paths.get(String.valueOf(file)), lines, Charset.defaultCharset());
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
