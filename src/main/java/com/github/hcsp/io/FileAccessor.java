package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        List<String> list = new ArrayList<>();
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt;
        while ((lineTxt = bufferedReader.readLine()) != null) {
            list.add(lineTxt);
        }
        bufferedReader.close();
        read.close();
        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        return FileUtils.readLines(file, StandardCharsets.UTF_8);

    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(Paths.get(file.getPath()));

    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws FileNotFoundException {
        PrintStream out = new PrintStream(file);
        for (String text : lines) {
            out.println(text);
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Files.write(Paths.get(file.getCanonicalPath()), lines);
    }

    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for (String text : lines) {
            bw.write(text);
            bw.newLine();
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
        writeLinesToFile4(lines, testFile);

        System.out.println(readFile1(testFile));
        System.out.println(readFile2(testFile));
        System.out.println(readFile3(testFile));
    }
}
