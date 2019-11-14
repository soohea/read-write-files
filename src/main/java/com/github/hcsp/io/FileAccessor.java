package com.github.hcsp.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws Exception {
        List<String> result = new ArrayList<>();
        try (InputStream is = new FileInputStream(file)) {
            StringBuilder temp = new StringBuilder();
            while (true) {
                int b = is.read();
                if (b == -1) {
                    break;
                }
                if (b != 012) {
                    temp.append((char) b);
                } else {
                    result.add(temp.toString());
                    temp = new StringBuilder();
                }
            }
        }
        return result;
    }

    public static List<String> readFile2(File file) throws Exception {
        List<String> result = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String content;
            while ((content = bufferedReader.readLine()) != null) {
                result.add(content);
            }
        }
        return result;
    }

    public static List<String> readFile3(File file) throws Exception {
        return Files.readAllLines(Paths.get(file.toURI()));
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws Exception {
        try (OutputStream outputStream = new FileOutputStream(file)) {
            for (String line : lines) {
                outputStream.write(line.getBytes());
                outputStream.write("\n".getBytes());
            }
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws Exception {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.write("\n");
            }
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws Exception {
        Files.write(Paths.get(file.toURI()), lines);
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
