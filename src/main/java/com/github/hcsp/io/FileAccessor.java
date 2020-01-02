package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class FileAccessor {
    public static List<String> readFile1(File file) throws FileNotFoundException {
        List<String> list = new ArrayList<>();
        FileInputStream fis = new FileInputStream(file);
        Scanner scanner = new Scanner(fis);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            list.add(line);
        }

        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        List<String> list = new ArrayList<>();
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        return list;
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> list = FileUtils.readLines(file, Charset.defaultCharset());
        return list;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        String s = String.join("\n", lines);
        byte b[] = s.getBytes();
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(b);
        fout.close();
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        String s = String.join("\n", lines);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(s);
        writer.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        String s = String.join("\n", lines);
        FileUtils.writeStringToFile(file, s, Charset.defaultCharset());
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
