package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {

    /**
     * 使用最原始的 FileInputStream，一个字符一个字符地读取
     *
     * @param file 待读取的文件
     * @return 将文件按行分割后的 List<String>
     * @throws IOException 如果读取时报错，比如没有权限，文件不存在等
     */
    public static List<String> readFile1(File file) throws IOException {
        List<String> result = new ArrayList<>();
        InputStream is = new FileInputStream(file);
        String line = "";
        while (true) {
            int b = is.read();
            if (b == -1) {
                break;
            }
            if (b != '\n') {
                line = String.join("", line, Character.toString((char) b));
            } else {
                result.add(line);
                line = "";
            }
        }
        return result;
    }

    /**
     * 使用 BufferedReader 一行一行地读取
     *
     * @param file 待读取的文件
     * @return 将文件按行分割后的 List<String>
     * @throws IOException 如果读取时报错，比如没有权限，文件不存在等
     */
    public static List<String> readFile2(File file) throws IOException {
        List<String> result = new ArrayList<>();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            result.add(line);
        }
        br.close();
        return result;
    }

    /**
     * 使用第三方库 Apache Commons IO 的 FileUtils 读取
     *
     * @param file 待读取的文件
     * @return 将文件按行分割后的 List<String>
     * @throws IOException 如果读取时报错，比如没有权限，文件不存在等
     */
    public static List<String> readFile3(File file) throws IOException {
        return FileUtils.readLines(file);
    }

    /**
     * 使用 Java 7+ 引入的 Files.write() 方法F
     *
     * @param file 待读取的文件
     * @return 将文件按行分割后的 List<String>
     * @throws IOException 如果读取时报错，比如没有权限，文件不存在等
     */
    public static List<String> readFile4(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    /**
     * 使用最原始的 FileOutputStream，一行一行地写入
     *
     * @param lines 待写入的数据
     * @param file  用来接收写入操作的文件
     * @throws IOException 如果读取时报错，比如没有权限，文件不存在等
     */
    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        for (String line : lines) {
            os.write(line.getBytes());
            os.write('\n');
        }
    }

    /**
     * 使用 BufferedWriter 一行一行地写入
     *
     * @param lines 待写入的数据
     * @param file  用来接收写入操作的文件
     * @throws IOException 如果读取时报错，比如没有权限，文件不存在等
     */
    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String line : lines) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    /**
     * 使用第三方库 Apache Commons IO 的 FileUtils 写入
     *
     * @param lines 待写入的数据
     * @param file  用来接收写入操作的文件
     * @throws IOException 如果读取时报错，比如没有权限，文件不存在等
     */
    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    /**
     * 使用 Java 7+ 引入的 Files.write() 方法
     *
     * @param lines 待写入的数据
     * @param file  用来接收写入操作的文件
     * @throws IOException 如果读取时报错，比如没有权限，文件不存在等
     */
    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
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
