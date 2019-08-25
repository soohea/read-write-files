package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) {
        List<String> result = new ArrayList<>();

        try {
            byte[] fileBytes = new byte[(int) file.length()];
            InputStream inputStream = new FileInputStream(file);
            inputStream.read(fileBytes); // store into fileBytes
            String[] lines = new String(fileBytes).split("\n");
            result = Arrays.asList(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<String> readFile2(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static List<String> readFile3(File file) throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    public static void writeLinesToFile1(List<String> lines, File file) {
        try (OutputStream fileOutputStream = new FileOutputStream(file)) {
            for (String line : lines) {
                fileOutputStream.write(line.getBytes());
                fileOutputStream.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(String.valueOf(file.toPath())));
            for (String string : lines) {
                bufferedWriter.write(string);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        System.out.println(readFile1(testFile));
        System.out.println(readFile2(testFile));
        System.out.println(readFile3(testFile));
    }
}
