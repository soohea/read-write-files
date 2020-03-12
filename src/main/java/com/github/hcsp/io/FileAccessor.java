package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class FileAccessor {
    private static List<String> list = new ArrayList<>();

    public static List<String> readFile1(File file) throws IOException {
        list.clear();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        while (line != null) {
            list.add(line);
            line = br.readLine();
        }
        return list;
    }

    public static List<String> readFile2(File file) throws FileNotFoundException {
        list.clear();
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            list.add(sc.nextLine());
        }
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        list.clear();
        Stream linesStream = Files.lines(file.toPath());
        linesStream.forEach(line -> {
            list.add((String) line);
        });
        return list;
    }

    public static void writeLinesToFile1(List<String> lines, File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        Files.write(Paths.get(file.getPath()), lines);
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
