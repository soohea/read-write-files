package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        List<String> list = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        int c = 0;
        while ((c = is.read()) != -1) {
            if (c == 10) {
                list.add(s.toString());
                s = new StringBuilder();
            } else {
                s.append((char) c);
            }
        }
        is.close();
        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> list = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        List lines = FileUtils.readLines(file, "UTF-8");
        return lines;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        for (String s : lines) {
            for (char c : s.toCharArray()) {
                os.write(c);
            }
            os.write(10);
        }
        os.close();
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
        writeLinesToFile2(lines, testFile);
        writeLinesToFile3(lines, testFile);

        System.out.println("jjj");

        System.out.println(readFile1(testFile));
        System.out.println("jjj");

        System.out.println(readFile2(testFile));
        System.out.println("jjj");
        System.out.println(readFile3(testFile));
    }
}
