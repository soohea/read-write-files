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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        // 声明输出流
        InputStream inputStream = new FileInputStream(file);
        // 声明输出流的缓存
        StringBuffer stringBuffer = new StringBuffer();
        // 读取输出流
        int readInt = inputStream.read();
        // 只要能读取
        while (readInt != -1) {
            // 写入输出流的缓存
            stringBuffer.append((char) readInt);
            readInt = inputStream.read();
        }
        // 将输出流的缓存转换成字符串
        String intputStr = new String(stringBuffer);
        // 对字符串根据空格进行分割，并添加到列表
        List<String> list = Arrays.asList(intputStr.split("\n"));
        // 输入流关闭
        inputStream.close();
        // 返回列表
        return list;
    }


    public static List<String> readFile2(File file) throws IOException {
        // 声明空的数组
        List<String> list = new ArrayList<>();
        // 声明BufferedReader的类对FileReader包装
        BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(file.toPath())));
        // 读取一行
        String lineStr = bufferedReader.readLine();
        while (lineStr != null) {
            // 将读取的行添加到列表，只要不为空就一直添加
            list.add(lineStr);
            lineStr = bufferedReader.readLine();
        }

        // 关闭bufferedReader
        bufferedReader.close();
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        // 声明文件输出流
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        for (String string : lines) {
            // 获取string的二进制字节流
            byte b[] = string.getBytes();
            // 输出流将二进制
            fileOutputStream.write(b);
            // 输入空白字符
            fileOutputStream.write('\n');
        }
        // 输出流关闭
        fileOutputStream.close();
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        // 用bufferedWriter类包装fileWriter类
        // fileWriter类 传入路径
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(String.valueOf(file.toPath())));
        String write = "";
        for (String string : lines) {
            write += string + "\n";
        }
        // bufferedWriter写入字符串
        bufferedWriter.write(write);
        // 关闭bufferedWriter
        bufferedWriter.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }

    public static void main(String[] args) throws IOException {

    }
}
