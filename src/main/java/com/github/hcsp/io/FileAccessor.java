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
        List<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        return FileUtils.readLines(file, "UTF-8");
    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(Paths.get(file.getAbsolutePath()));
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        Files.write(Paths.get(file.getAbsolutePath()), lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, "UTF-8", lines);
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException { //通过传入File实例化Scanner类
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (String line : lines) {
            bufferedWriter.write(line + System.lineSeparator());
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
