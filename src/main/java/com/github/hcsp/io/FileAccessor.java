package com.github.hcsp.io;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class FileAccessor {

    public static List<String> readFile1(File file) {
        List<String> lines = null;
        try (FileInputStream fileInputStream = new FileInputStream(file);) {
            lines = IOUtils.readLines(fileInputStream, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static List<String> readFile2(File file) {
        List<String> list = new ArrayList();
        String line = new String();
        int b = -1;
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            while ((b = fileInputStream.read()) != -1) {
                if ((char) b != '\n') {
                    line += String.valueOf((char) b);
                } else {
                    list.add(line);
                    line = "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;

    }

    public static List<String> readFile3(File file) {
        List<String> list = new ArrayList();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));) {
            String line = null;
            line = bufferedReader.readLine();
            while (line != null) {
                list.add(line);
                try {
                    line = bufferedReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void writeLinesToFile1(List<String> lines, File file) {
        for (String line : lines) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file, true);) {
                IOUtils.write(line + "\n", fileOutputStream, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    public static void writeLinesToFile2(List<String> lines, File file) {
        for (String line : lines) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file, true)) {
                final byte[] bytes = (line + "\n").getBytes();
                fileOutputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeLinesToFile3(List<String> lines, File file) {
        for (String line : lines) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
                bufferedWriter.write(line + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
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
