package com.github.hcsp.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        List<String> result = new ArrayList<>();
        InputStream is = new FileInputStream(file);
        String line = "";
        while (true) {
            int b = is.read();
            if (b != '\n') {
//                line += Character.toString((char) b);
                line = String.join("", line, Character.toString((char) b));
                System.out.println(line);
            } else {
                result.add(line);
                line = "";
            }
            if (b == -1) {
                break;
            }
        }
        return result;
    }
//    public static List<String> readFile2(File file) {}
//
//    public static List<String> readFile3(File file) {}

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        for (String line : lines) {
            os.write(line.getBytes());
            os.write('\n');
        }
    }

//    public static void writeLinesToFile2(List<String> lines, File file) {}
//
//    public static void writeLinesToFile3(List<String> lines, File file) {}

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("AAA", "BBB", "CCC");
        writeLinesToFile1(lines, testFile);
//        writeLinesToFile2(lines, testFile);
//        writeLinesToFile3(lines, testFile);

        System.out.println(readFile1(testFile));
//        System.out.println(readFile2(testFile));
//        System.out.println(readFile3(testFile));
    }
}
