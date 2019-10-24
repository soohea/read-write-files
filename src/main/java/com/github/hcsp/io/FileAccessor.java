package com.github.hcsp.io;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        List<String> list = new ArrayList<>();
        try (InputStream is = new FileInputStream(file)) {
            int bytes;
            StringBuilder sb = new StringBuilder();
            while ((bytes = is.read()) != -1) {
                if (bytes == '\n') {
                    list.add(sb.toString());
                    sb.delete(0, sb.length());
                } else {
                    sb.append((char) bytes);
                }
            }
        } catch (IOException e) {
            throw new IOException();
        }
        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> list = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (null != (line = br.readLine())) {
                list.add(line);
            }
        } catch (IOException e) {
            throw new IOException();
        }

        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> list = new ArrayList<>();
        try {
            list =  Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new IOException();
        }
        return list;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        try (OutputStream os = new FileOutputStream(file)) {
            for (String line : lines) {
                os.write(line.getBytes());
                os.write('\n');
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new IOException();
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        try {
            Files.write(file.toPath(), lines);
        } catch (IOException e) {
            throw new IOException();
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
