package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws Exception {
        //使用BufferReader读取
        List<String> readTest = new ArrayList<>();
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
             BufferedReader br = new BufferedReader(reader)) {
            String linTest;
            while ((linTest = br.readLine()) != null) {
                readTest.add(linTest);
            }
        }
        return readTest;
    }

    public static List<String> readFile2(File file) throws IOException {
        //使用commons包
        return FileUtils.readLines(file, "UTF-8");
    }

    public static List<String> readFile3(File file) throws Exception {
        //使用字节流
        List<String> readTest = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            StringBuilder stringBuilder = new StringBuilder();
            int len;
            while ((len = fileInputStream.read()) != -1) {
                if (len == 10) {
                    readTest.add(stringBuilder.toString());
                    stringBuilder.delete(0, stringBuilder.length());
                    continue;
                }
                stringBuilder.append((char) len);
            }
        }
        return readTest;
    }

    public static List<String> readFile4(File file) throws Exception {
        //使用java7NIO
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();
        int capacity = 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        int len;
        String str = null;
        while ((len = fileChannel.read(byteBuffer)) != -1) {
            byteBuffer.clear();
            byte[] array = byteBuffer.array();
            str = new String(array, 0, len);
        }
        if (str == null) {
            return null;
        }
        String[] split = str.split("\n");
        return Arrays.asList(split);
    }

    public static void writeLinesToFile1(List<String> lines, File file) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
             BufferedWriter br = new BufferedWriter(writer)) {
            for (String test : lines) {
                br.write(test + "\n");
            }
        } catch (Exception e) {
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
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {

            byte[] flush;
            for (String s : lines) {
                flush = (s + "\n").getBytes();
                fileOutputStream.write(flush, 0, flush.length);
            }
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesToFile4(List<String> lines, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            FileChannel channel = fileOutputStream.getChannel();
            for (String s : lines) {
                ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(s + "\n");
                channel.write(byteBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws Exception {
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
