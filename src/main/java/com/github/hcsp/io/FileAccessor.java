package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws FileNotFoundException {
        List<String> result = new ArrayList<>();
        try (InputStream is = new FileInputStream(file)) {
            byte[] all = new byte[(int) file.length()];
            is.read(all);
            String[] lines = new String(all).split("\n");
            result = Arrays.asList(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String> readFile2(File file) {
        List<String> result = new ArrayList<>();
        String line = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String> readFile3(File file) throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }

    public static List<String> readFile4(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static List<String> readFile5(File file) throws IOException {
        List<String> result = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        try (InputStream is = new FileInputStream(file)) {
            int oneChar;
            while ((oneChar = is.read()) != -1) {
                if (oneChar != '\n' && oneChar != '\r') {
                    sb.append((char) oneChar);
                } else {
                    result.add(sb.toString());
                    sb.delete(0, sb.length());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws FileNotFoundException {
        try (OutputStream os = new FileOutputStream(file)) {
            for (String line : lines) {
                os.write(line.getBytes());
                os.write('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeLinesToFile2(List<String> lines, File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

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

        System.out.println(readFile1(testFile));
        System.out.println(readFile2(testFile));
        System.out.println(readFile3(testFile));
    }
}
