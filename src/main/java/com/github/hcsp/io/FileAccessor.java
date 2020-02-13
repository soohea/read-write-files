package com.github.hcsp.io;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    //NIO
    public static List<String> readFile1(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }
    //Apache Commons IO
    public static List<String> readFile2(File file) throws IOException {
        return IOUtils.readLines(new FileInputStream(file), "UTF-8");
    }
    //BufferReader
    public static List<String> readFile3(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> list = new ArrayList<>();
        String tmp;
        while ((tmp = br.readLine()) != null) {
            list.add(tmp);
        }
        br.close();
        return list;
    }

    //1. NIO
    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }
    //2. Apache Commons IO
    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        IOUtils.writeLines(lines, IOUtils.LINE_SEPARATOR, new FileOutputStream(file), "utf-8");
    }
    //3. BufferWriter
    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for (String tmp:lines){
            bw.write(tmp+"\n");
        }
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
