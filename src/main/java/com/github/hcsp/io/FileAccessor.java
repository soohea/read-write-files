package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        StringBuilder sb = new StringBuilder();
        List<String> list = new ArrayList<>();
        String content = "";
        while ((content = br.readLine()) != null) {
            list.add(content);
        }
        fr.close();
        br.close();
        return list;

    }

    public static List<String> readFile2(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        int length = inputStream.available();
        byte bytes[] = new byte[length];
        inputStream.read(bytes);
        inputStream.close();
        String str = new String(bytes, StandardCharsets.UTF_8);
        return Arrays.asList(str.split("\r\n"));
    }

    public static List<String> readFile3(File file) throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        StringBuffer bf = new StringBuffer();
        for (String line : lines) {
            bf.append(line).append("\r\n");
        }
        OutputStream os = new FileOutputStream(file);
        os.write(bf.toString().getBytes());
        os.close();
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        PrintStream ps = new PrintStream(new FileOutputStream(file));
        StringBuffer bf = new StringBuffer();
        for (String line : lines) {
            bf.append(line).append("\r\n");
        }
        ps.append(bf.toString());
        ps.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        StringBuffer bf = new StringBuffer();
        for (String line : lines) {
            bf.append(line).append("\r\n");
        }
        FileUtils.writeStringToFile(file, bf.toString(), Charset.defaultCharset());
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
