package com.github.hcsp.io;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {

    public static List<String> readFile1(File file) throws IOException {
        FileInputStream read = new FileInputStream(file);
        StringBuilder stringBuilder = new StringBuilder();
        int temp;
        while ((temp = read.read()) != -1){
            stringBuilder.append((char) temp);
        }
        read.close();
        return Arrays.asList(stringBuilder.toString().split("\r\n"));
    }

    public static List<String> readFile2(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        List<String> lists = new ArrayList<>();
        String list;
        while ((list = bufferedReader.readLine()) != null){
            lists.add(list);
        }
        bufferedReader.close();
        return lists;
    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileOutputStream write = new FileOutputStream(file);
        for (String line : lines){
            write.write(line.getBytes());
            write.write("\r\n".getBytes());
        }
        write.close();
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (String line : lines){
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }
}

