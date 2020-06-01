package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        return FileUtils.readLines(file);
    }

    public static List<String> readFile2(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static List<String> readFile3(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        List<String> list = new ArrayList<>();
        String str = " ";
        while ((str = br.readLine()) != null) {
            list.add(str);
        }
        br.close();
        return list;
    }

    public static List<String> readFile4(File file) throws IOException {
        StringBuilder s = new StringBuilder();
        InputStream is = new FileInputStream(file);
        int b;
        while ((b = is.read()) != -1) {
            s.append((char) b);
        }
        return Arrays.asList(s.toString().split("\\r\\n"));
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String line : lines) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : lines) {
            //因为字节是一个一个地读，所有要自行添加换行符
            stringBuilder.append(str).append("\r\n");
        }
        os.write(stringBuilder.toString().getBytes());
        os.close();
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
