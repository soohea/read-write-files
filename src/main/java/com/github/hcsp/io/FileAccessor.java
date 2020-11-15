package com.github.hcsp.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    // 1. FileInputStream
    public static List<String> readFile1(File file) throws IOException {
        List<String> result = new ArrayList<>();
        try (FileInputStream fi = new FileInputStream(file)) {
            byte[] all_bytes = new byte[(int) file.length()];
            StringBuilder sb = new StringBuilder();
            fi.read(all_bytes);
            for (byte b :
                    all_bytes) {
                if (b == '\n' || b == '\r') {
                    if (sb.length() > 0) {
                        result.add(sb.toString());
                        sb.setLength(0);
                    }
                } else {
                    sb.append((char) b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        // StringUti
        return result;
    }

    // 2. BufferedReader
    public static List<String> readFile2(File file) throws IOException {
        List<String> result = new ArrayList<>();
        try (BufferedReader bw = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = bw.readLine()) != null) {
                result.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return result;
    }

    // 3.Files.readAllLines()
    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // 1. FileOutputStream
    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        try (FileOutputStream fs = new FileOutputStream(file)) {
            for (String data :
                    lines) {
                fs.write(data.getBytes());
                fs.write('\n');
                // fs.write("\n".getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // 2. BufferWriter
    public static void writeLinesToFile2(List<String> lines, File file) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String data
                    : lines) {
                bw.write(data);
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // 3. Files.write()
    public static void writeLinesToFile3(List<String> lines, File file) throws Exception {
        Files.write(file.toPath(), lines, StandardOpenOption.CREATE);
    }

    public static void main(String[] args) throws Exception {
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
