package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    public static List<String> readFile2(File file) throws IOException {
        Path path = Paths.get(file.getPath());
        return Files.readAllLines(path);

    }

    public static List<String> readFile3(File file) throws IOException {
        FileReader istream = new FileReader(file);
        BufferedReader in = new BufferedReader(istream);
        String line;
        List<String> result = new ArrayList<String>();
        while ((line = in.readLine()) != null) {
            result.add(line);
        }
        return result;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        Path path = Paths.get(file.getPath());
        Files.write(path, lines, Charset.defaultCharset());
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        for (int i = 0; i < lines.size(); i++) {
            writer.write(lines.get(i));
            if (i < lines.size()) {
                writer.newLine();
            }
        }
        writer.close();
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
