package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        String fileContent = FileUtils.readFileToString(file, "utf-8");
        return Arrays.asList(fileContent.split("\\n"));
    }

    public static List<String> readFile2(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        String fileContent = "";
        int byteNumber;
        while ((byteNumber = inputStream.read()) != -1) {
            fileContent += ((char) byteNumber);
        }
        inputStream.close();
        return Arrays.asList(fileContent.split("\\n"));
    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static List<String> readFile4(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(String.join("\n", lines).getBytes());
        outputStream.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }

    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        for (String line : lines) {
            writer.write(line);
            writer.newLine();
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
        writeLinesToFile4(lines, testFile);

        System.out.println(readFile1(testFile));
        System.out.println(readFile2(testFile));
        System.out.println(readFile3(testFile));
        System.out.println(readFile4(testFile));
    }
}
