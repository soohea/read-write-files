package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
  public static List<String> readFile1(File file) throws IOException {
    FileInputStream fis = new FileInputStream(file);
    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
    List<String> list = new ArrayList();

    while (true) {
      String text = br.readLine();
      if (text == null) {
        break;
      }
      list.add(text);
    }

    return list;
  }

  public static List<String> readFile2(File file) throws IOException {
    return Files.readAllLines(file.toPath());
  }

  public static List<String> readFile3(File file) throws IOException {
    byte[] bytes = FileUtils.readFileToByteArray(file);
    String[] stringList = (new String((bytes))).split("\n");
    List list = Arrays.asList(stringList);

    return list;
  }

  public static List<String> readFile4(File file) throws IOException {
    List<String> list = FileUtils.readLines(file, StandardCharsets.UTF_8);
    return list;
  }

  public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
    FileOutputStream fos = new FileOutputStream(file);
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
    for (String line : lines) {
      bw.write(line);
      bw.newLine();
    }
    bw.close();
  }

  public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
    Files.write(file.toPath(), lines);
  }

  public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
    String res = "";
    for (String line : lines) {
      res += (line + "\n");
    }
    FileUtils.write(file, res, StandardCharsets.UTF_8);
  }

  public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
    FileUtils.writeLines(file, lines);
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
