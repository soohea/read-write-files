package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    /*
     * 1.By Apache Commons IO
     */
    public static List<String> readFile1(File file) throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    /*
     * 2.By FileInputStream
     */
    public static List<String> readFile2(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        List<String> list = new ArrayList();
        String s = "";
        while (true) {
            int readByte = inputStream.read();
            if (readByte == -1) {
                break;
            }
            char c = (char) readByte;
            if (c == '\r') {
                inputStream.read();
                list.add(s);
                s = "";
            } else {
                s += (char) readByte;
            }

        }
        return list;
    }

    /*
     * 3.By BufferedReader
     */
    public static List<String> readFile3(File file) throws IOException {
        BufferedReader bufferedReader = Files.newBufferedReader(file.toPath());
        List<String> list = new ArrayList();
        while (true) {
            String lineString = bufferedReader.readLine();
            if (lineString == null) {
                break;
            }
            list.add(lineString);
        }
        return list;
    }

    /*
     * 4.By NIO Files
     */
    public static List<String> readFile4(File file) throws IOException {
        return Files.readAllLines(file.toPath(), Charset.defaultCharset());
    }

    /*
     * 1.By Apache Commons IO
     */
    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        IOUtils.writeLines(lines, "\r\n", outputStream, Charset.defaultCharset());
    }

    /*
     * 2.By OutputStream
     */
    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                outputStream.write(line.charAt(i));
            }
            outputStream.write(new byte[]{'\r', '\n'});
        }
    }

    /*
     * 3.By BufferedWriter
     */
    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        BufferedWriter bufferedWriter = Files.newBufferedWriter(file.toPath());
        for (String line : lines) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    /*
     * 4.By NIO Files
     */
    private static void writeLinesToFile4(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
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
