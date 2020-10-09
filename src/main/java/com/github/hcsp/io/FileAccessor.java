package com.github.hcsp.io;

import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        List<String> strings = null;
        strings = Files.readAllLines(Paths.get(file.getPath()));

        return strings;
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> strings = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream fileInputStream = new FileInputStream(file);
        while (true) {
            int i = fileInputStream.read();
            if (i == -1) {
                break;
            }
            stringBuilder.append((char) i);
        }
        String[] strArr = stringBuilder.toString().split(System.lineSeparator());
        strings.addAll(Arrays.asList(strArr));

        return strings;

    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> strings = new ArrayList<>();
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        while (true) {
            String s = bufferedReader.readLine();
            if (s == null) {
                break;
            }
            strings.add(s);
        }

        return strings;
    }

    public static void writeLinesToFile1(List<String> lines, File file) {
        try {
            Files.write(file.toPath(), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            for (String line : lines) {
                byte[] bytes = line.getBytes();
                fileOutputStream.write(bytes);
                fileOutputStream.write(System.lineSeparator().getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
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
