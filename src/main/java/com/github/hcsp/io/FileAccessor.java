package com.github.hcsp.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        int len = 0;
        InputStream stream1 = null;
        List<String> list = new ArrayList<>();
        StringBuilder sBuilder = new StringBuilder();
        stream1 = new FileInputStream(file);
        while ((len = stream1.read()) != -1) {
            if (len == 10) {
                list.add(sBuilder.toString());
                sBuilder.delete(0, sBuilder.length());

            } else {
                sBuilder.append((char) len);
            }
        }
        stream1.close();
        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        FileReader fReader;
        List<String> list = new ArrayList<>();
        fReader = new FileReader(file);
        BufferedReader bReader = new BufferedReader(fReader);
        String str;
        while ((str = bReader.readLine()) != null) {
            list.add(str);
        }
        bReader.close();
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> list = new ArrayList<>();
        list = Files.readAllLines(file.toPath());
        return list;
    }

    public static void writeLinesToFile1(List<String> lines, File file) {
        OutputStream stream1 = null;
        try {
            stream1 = new FileOutputStream(file);
            for (String str : lines) {
                byte[] bytes = str.getBytes();
                for (int i = 0; i < bytes.length; i++) {
                    stream1.write(bytes[i]);
                }
                stream1.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream1.flush();
                stream1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeLinesToFile2(List<String> lines, File file) {
        FileWriter fWriter;
        try {
            fWriter = new FileWriter(file, true);
            BufferedWriter bWriter = new BufferedWriter(fWriter);
            for (String str : lines) {
                bWriter.write(str);
                bWriter.append('\n');
            }
            bWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) {
        try {
            for (String str : lines) {
                str = str + '\n';
                byte[] bytes = str.getBytes();
                Files.write(file.toPath(), bytes, StandardOpenOption.APPEND);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
