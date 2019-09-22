package com.github.hcsp.io;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        return Files.readAllLines(Paths.get(file.toURI()));
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null){
            result.add(line);
        }
        return result;
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> result = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = fis.read()) != -1){
            char c = (char) len;
            if (c == '\n'){
                result.add(sb.toString());
                sb = new StringBuilder();
            }else {
                sb.append(c);
            }
        }
        return result;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        Files.write(Paths.get(file.toURI()), lines, StandardOpenOption.CREATE);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        for (String line : lines){
            fw.write(line);
            fw.write("\n");
        }
        fw.flush();
        fw.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        for (String line : lines){
            fos.write(line.getBytes());
            fos.write("\n".getBytes());
        }
        fos.flush();
        fos.close();
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
