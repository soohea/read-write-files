package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) {
        List<String> res = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file)) {
            String str = "";
            while (fis.available() > 0) {
                str += (char) fis.read();
            }
            res = Arrays.asList(str.split("[\n]"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return res;
        }
    }

    public static List<String> readFile2(File file) {
        List<String> res = new ArrayList<>();
        try {
            res = FileUtils.readLines(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static List<String> readFile3(File file) {
        List<String> res = new ArrayList<>();
        try {
            res = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void writeLinesToFile1(List<String> lines, File file) {
        try {
            OutputStream fos = new FileOutputStream(file);
            lines.forEach(l -> {
                Arrays.asList(l.split("")).forEach(c -> {
                    try {
                        fos.write((int) c.charAt(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                try {
                    fos.write('\n');
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) {
        try {
            FileUtils.writeLines(file, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) {
        try {
            Files.write(file.toPath(), lines);
        } catch (IOException e) {
            e.printStackTrace();
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
