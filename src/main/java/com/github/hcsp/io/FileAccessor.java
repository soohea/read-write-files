package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        //Io包
        return FileUtils.readLines(file, "utf-8");
    }

    public static List<String> readFile2(File file) throws IOException {
        //文件字节流
        FileInputStream input = new FileInputStream(file);
        int temp;
        StringBuilder sb = new StringBuilder();
        while ((temp = input.read()) != -1) {
            sb.append((char) temp);
        }
        input.close();
        return Arrays.asList(sb.toString().split("\\r\\n"));
    }

    public static List<String> readFile3(File file) throws IOException {
        //缓冲字符流
        BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
        List<String> lines = new ArrayList<>();
        String str = null;
        while ((str = reader.readLine()) != null) {
            lines.add(str);
        }
        reader.close();
        return lines;
    }

    public static List<String> readFile4(File file) throws IOException {
        //NIO
        return Files.readAllLines(Paths.get(file.getAbsolutePath()));
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        //Io包
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        //文件字节流
        FileOutputStream output = new FileOutputStream(file);
        for (String line : lines) {
            output.write(line.getBytes());
            output.write("\r\n".getBytes());
        }
        output.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        //缓冲字符流
        BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
        for (String line : lines
        ) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
        //NIO
        Files.write(Paths.get(file.getAbsolutePath()), lines);
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
        System.out.println(readFile4(testFile));
    }
}
