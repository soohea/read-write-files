package com.github.hcsp.io;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        return IOUtils.readLines(new FileInputStream(file), Charset.defaultCharset());
    }

    public static List<String> readFile2(File file) throws IOException {
        return Files.readAllLines(file.toPath());

    }

    public static List<String> readFile3(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader reader = new BufferedReader(fr);
        String strCurrentLine;
        List<String> results = new ArrayList<>();
        while ((strCurrentLine = reader.readLine()) != null) {
            results.add(strCurrentLine);
        }
        reader.close();

        return results;
    }

    public static List<String> readFile4(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        StringBuilder result = new StringBuilder();
        int byteInt;
        while ((byteInt = in.read()) != -1) {
            result.append((char) byteInt);
        }
        in.close();
        return Arrays.asList(result.toString().split("\\r\\n"));
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException {
        FileUtils.writeLines(file, lines);
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        //因为是一个字节一个字节地读，所以需要自行添加换行符
        StringBuilder bf = new StringBuilder();
        for (String line : lines) {
            bf.append(line).append("\r\n");
        }
        os.write(bf.toString().getBytes());
        os.close();


//        for (String line : lines) {
//            Files.write(file.toPath(), line.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
//        }
//

    }

    public static void writeLinesToFile3(List<String> lines, File file) throws IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String line : lines) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();


    }

    public static void writeLinesToFile4(List<String> lines, File file) throws IOException {
        Files.write(file.toPath(), lines);
    }

    public static void main(String[] args) throws IOException {
        File projectDir = new File(System.getProperty("basedir", System.getProperty("user.dir")));
        File testFile = new File(projectDir, "target/test.txt");
        //List<String> lines = new ArrayList<>(Arrays.asList("AAA", "BBB", "CCC"));
        List<String> list = Arrays.asList("a", "b", " ", "   ", "c");
        writeLinesToFile1(list, testFile);
        System.out.println(readFile1(testFile));

        writeLinesToFile2(list, testFile);
        System.out.println(readFile1(testFile));

        writeLinesToFile3(list, testFile);
        System.out.println(readFile3(testFile));

        writeLinesToFile4(list, testFile);
        System.out.println(readFile4(testFile));
    }
}
