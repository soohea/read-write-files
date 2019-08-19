package com.github.hcsp.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        List<String> resultList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        int read;
        while ((read = fileInputStream.read()) != -1) {
            if (read == '\n') {
                resultList.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            } else {
                stringBuilder.append((char) read);
            }
        }
        return resultList;
    }

    public static List<String> readFile2(File file) {
        BufferedReader bufferedReader = null;
        List<String> resultList = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                resultList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultList;
    }

    public static List<String> readFile3(File file) throws IOException {
        String path = String.valueOf(file);
        return Files.readAllLines(Paths.get(path));
    }

    public static void writeLinesToFile1(List<String> lines, File file) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            for (String s : lines) {
                fileOutputStream.write(s.getBytes());
                fileOutputStream.write(new byte[]{'\n'});
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (String s : lines) {
            bufferedWriter.write(s);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        bufferedWriter.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        String p = String.valueOf(file);
        Path write = Files.write(Paths.get(p), lines);
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
