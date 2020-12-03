package com.github.hcsp.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        InputStream is = new FileInputStream(file);
        StringBuilder str = new StringBuilder();
        while (true) {
            int b = is.read();
            if (b == -1) {
                break;
            } else {
                str.append((char) b);
            }
        }
        return Arrays.asList(str.toString().split("\n"));
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        while (true) {
            String str = br.readLine();
            if (str == null) {
                break;
            }
            lines.add(str);
        }
        return lines;
    }

    public static List<String> readFile3(File file) throws IOException {
        Path path = Paths.get(file.getPath());
        return Files.readAllLines(path, Charset.defaultCharset());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        for (String str : lines) {
            for (int i = 0; i < str.length(); i++) {
                os.write(str.charAt(i));
            }
            os.write('\n');
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for (String str : lines) {
            bw.write(str + "\n");
        }
        bw.flush();
        bw.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Path path = Paths.get(file.getPath());
        Files.write(path, lines);
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
