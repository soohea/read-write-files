package com.github.hcsp.io;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        return IOUtils.readLines(is, Charset.defaultCharset());

    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> result = new ArrayList<>();

        InputStream is = new FileInputStream(file);

        int b;
        String line = "";
        while (true) {
            b = is.read();

            if (b != -1 && (char) b != '\n') {

                line += String.valueOf((char) b);

            } else if ((char) b == '\n') {

                result.add(line);
                line = "";

            } else {

                break;

            }
        }

        return result;
    }


    public static List<String> readFile3(File file) throws IOException {
        List<String> result = new ArrayList<>();

        InputStream is = new FileInputStream(file);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

        String line;
        while (true) {
            line = bufferedReader.readLine();

            if (line != null) {
                result.add(line);
            } else {
                break;
            }

        }

        return result;
    }

    public static List<String> readFile4(File file) throws IOException {

        return Files.readAllLines(file.toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);

        IOUtils.writeLines(lines, "\n", os, Charset.defaultCharset());
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);

        lines.stream().forEach(line -> {
            try {
                StringBuffer buffer = new StringBuffer(line);

                for (int i = 0; i < buffer.length(); i++) {

                    os.write((int) buffer.charAt(i));

                }

                os.write((int) '\n');

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));

        lines.stream().forEach(line -> {
            try {

                bufferedWriter.write(line);
                bufferedWriter.write('\n');

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //刷新流
        bufferedWriter.flush();

        //关闭资源
        bufferedWriter.close();

    }

    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {

        Files.write(file.toPath(), lines, Charset.defaultCharset());

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
