package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        return FileUtils.readLines(file, "UTF-8");
    }

    public static List<String> readFile2(File file) throws IOException {
        Path path = file.toPath();
        Stream<String> ss = Files.lines(path);
        Object[] objects = ss.toArray();
        String[] stringArray = Arrays.copyOf(objects, objects.length, String[].class);
        return Arrays.asList(stringArray);
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> lines = new ArrayList();
        String line = null;
        FileInputStream is= new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(is, "GBK");
        BufferedReader br = new BufferedReader(reader);
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        br.close();
        is.close();
        return lines;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        for (int i = 0; i < lines.size(); i++){
            byte[] bytes = lines.get(i).getBytes();
            for (int j = 0; j < bytes.length; j++){
                fileOutputStream.write(bytes[j]);
            }
            fileOutputStream.write(("\n").getBytes());
        }
        fileOutputStream.flush(); //强制刷新输出流
        fileOutputStream.close(); //强制关闭输出流
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < lines.size(); i++){
            bw.write(lines.get(i));
            bw.write("\n");
        }
        bw.close();
        fw.close();
    }

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        List<String> lines = Arrays.asList("AAA", "BBB", "CCC");
//        writeLinesToFile1(lines, testFile);
//        writeLinesToFile2(lines, testFile);
        writeLinesToFile3(lines, testFile);

//        System.out.println(readFile1(testFile));
        System.out.println(readFile2(testFile));
//        System.out.println(readFile3(testFile));
    }
}
