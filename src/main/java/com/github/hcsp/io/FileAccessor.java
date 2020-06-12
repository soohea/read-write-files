package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        List<String> result = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        int b = is.read();
        while (b != -1) {
            // 如果还在同一行，则添加到辅助字符串中。如果是另起一行，添加到数组中，并置空辅助字符串。
            if (b != '\n') {
                s.append((char) b);
            } else {
                if (s.length() != 0) {
                    result.add(s + "");
                }
                s.delete(0, s.length());
            }
            b = is.read();
        }
        return result;
    }

    public static List<String> readFile2(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> result = new ArrayList<>();
        String contentLine = reader.readLine();
        while (contentLine != null) {
            result.add(contentLine);
            contentLine = reader.readLine();
        }
        return result;
    }

    public static List<String> readFile3(File file) throws IOException {
        return FileUtils.readLines(file, StandardCharsets.UTF_8);
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileOutputStream os = new FileOutputStream(file);
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                os.write(line.charAt(i));
            }
            os.write('\n');
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("AAA", "BBB", "CCC");
        writeLinesToFile1(lines, testFile);
        //writeLinesToFile2(lines, testFile);
        //writeLinesToFile3(lines, testFile);

        System.out.println(readFile1(testFile));
        //System.out.println(readFile2(testFile));
        //System.out.println(readFile3(testFile));
    }
}
