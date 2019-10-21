package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        List<String> list = new ArrayList<>();
        FileInputStream is = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader bf = new BufferedReader(isr);

        for (String a = bf.readLine(); a != null; a = bf.readLine()) {
            list.add(a);
        }
        bf.close();
        isr.close();
        is.close();
        return list;
    }

    public static List<String> readFile2(File file) throws IOException {
        return FileUtils.readLines(file, "UTF-8");
    }

    public static List<String> readFile3(File file) throws IOException {
        List<String> list = new ArrayList<>();
        FileInputStream is = new FileInputStream(file);

        for (int a = is.read(); a != -1; a = is.read()) {
            list.add(String.valueOf((char) a));
        }
        is.close();
        return list;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileOutputStream fo = new FileOutputStream(file);
        OutputStreamWriter opw = new OutputStreamWriter(fo);
        BufferedWriter br = new BufferedWriter(opw);
        String a = "\n";
        for (String line : lines) {
            br.write(line);
            br.write(a);
            br.flush();
        }
        br.close();
        opw.close();
        fo.close();
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileOutputStream fo = new FileOutputStream(file);
        String a = "\n";
        byte[] b = a.getBytes();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            fo.write(line.getBytes());
            fo.write(b);
            fo.flush();
        }
        fo.close();
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
