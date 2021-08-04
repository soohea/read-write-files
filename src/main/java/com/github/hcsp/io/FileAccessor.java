package com.github.hcsp.io;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        int newByte;
        StringBuilder newTextLine = new StringBuilder();
        List<String> textLines = new ArrayList<>();
        while ((newByte = fileInputStream.read()) != -1) {
            if ( (char) newByte == '\r' || (char) newByte == '\n') {
                textLines.add(newTextLine.toString());
                newTextLine.delete(0, newTextLine.length());
            } else {
                newTextLine.append((char) newByte);
            }
        }
        fileInputStream.close();
        return textLines;
    }

    public static List<String> readFile2(File file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        String newTextLine;
        List<String> textLines = new ArrayList<>();
        while ((newTextLine = in.readLine()) != null){
            textLines.add(newTextLine);
        }
        in.close();
        return  textLines;
    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        for (String line:lines) {
            for (int i = 0; i < line.length(); i++) {
                out.write(line.charAt(i));
            }
            out.write('\n');
        }
        out.close();
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        for (String line:lines) {
            out.write(line);
            out.write('\n');
        }
        out.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("AAA", "BBB", "CCC");
        //List<String> lines = Arrays.asList("a", "b", " ", "   ", "c");

        writeLinesToFile1(lines, testFile);
        writeLinesToFile2(lines, testFile);
        writeLinesToFile3(lines, testFile);

        System.out.println(readFile1(testFile));
        System.out.println(readFile2(testFile));
        System.out.println(readFile3(testFile));
    }
}
