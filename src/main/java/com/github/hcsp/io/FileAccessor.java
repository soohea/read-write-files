package com.github.hcsp.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        List<String> result;
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = new FileInputStream(file);
        while (true) {
            int i = inputStream.read();
            if (i == -1) {
                String string = new String(stringBuffer);
                result = Arrays.asList(string.split("\r\n"));
                break;
            }
            char a = (char) i;
            stringBuffer.append(a);
        }
        return result;
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> result = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = "";
        while (true) {
            line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            result.add(line);
        }
        return result;
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> result = new ArrayList<>();
        LineIterator lineIterator = FileUtils.lineIterator(file);
        while (lineIterator.hasNext()) {
            result.add(lineIterator.next());
        }
        return result;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        for (int i = 0; i < lines.size(); i++) {
            byte[] b = lines.get(i).getBytes();
            outputStream.write(b);
            if (i != lines.size() - 1) {
                outputStream.write("\r\n".getBytes());
            }
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        for (int i = 0; i < lines.size(); i++) {
            bufferedWriter.write(lines.get(i));
            if (i != lines.size() - 1) {
                bufferedWriter.newLine();
            }
        }
        bufferedWriter.flush();
        bufferedWriter.close();
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
