package com.github.hcsp.io;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to read the file from " + file.getAbsolutePath());
        }
    }

    public static List<String> readFile2(File file) {
        List<String> result = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            StringBuilder currLine = new StringBuilder();
            while (true) {
                int next = inputStream.read();
                if (next == -1) {
                    break;
                } else if ((char) next == '\n') {
                    result.add(currLine.toString());
                    currLine.setLength(0);
                } else {
                    currLine.append((char) next);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to read the file from " + file.getAbsolutePath());
        }
        return result;
    }

    public static List<String> readFile3(File file) {
        List<String> result = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                } else {
                    result.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to read the file from " + file.getAbsolutePath());
        }
        return result;
    }

    public static void writeLinesToFile1(List<String> lines, File file) {
        try {
            Files.write(file.toPath(), lines);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to write to file " + file.getAbsolutePath());
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            for (String line : lines) {
                byte[] currLine = new byte[line.length() + 1];
                currLine[line.length()] = '\n';
                for (int i = 0; i < line.length(); i++) {
                    currLine[i] = (byte) line.charAt(i);
                }
                outputStream.write(currLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to write to file " + file.getAbsolutePath());
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to write to file " + file.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
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
