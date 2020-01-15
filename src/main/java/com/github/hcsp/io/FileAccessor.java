package com.github.hcsp.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws FileNotFoundException {
        InputStream is = new FileInputStream(file);
        is.
    }

    public static List<String> readFile2(File file) {}

    public static List<String> readFile3(File file) {}

    public static void writeLinesToFile1(List<String> lines, File file) {}

    public static void writeLinesToFile2(List<String> lines, File file) {}

    public static void writeLinesToFile3(List<String> lines, File file) {}

    public static void main(String[] args) throws Exception{
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
