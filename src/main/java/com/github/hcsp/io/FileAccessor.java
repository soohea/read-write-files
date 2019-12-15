package com.github.hcsp.io;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FileAccessor {
    public static List<String> readFile1(File file) throws IOException {
        List<String> list = new LinkedList();
        InputStream is = new FileInputStream(file);
        StringBuilder sb = new StringBuilder();
        while (true) {
            int b = is.read();
            if (b==-1) {
                break;
            }
            sb.append((char) b+"");
        }
        is.close();
        //全部读出来，然后按换行符分割。。。
        list.addAll(Arrays.asList(sb.toString().split("\r\n")));
        return list;
    }

    public static List<String> readFile2(File file) throws IOException{
        List<String> list = new LinkedList();
        BufferedReader br = new BufferedReader( new FileReader(file) ); //内部也是自己创建了一个缓冲区，不过是用编程的方式实现的
        while (true) {
            String line = br.readLine();
            if (line==null) {
                break;
            }
            list.add(line);
        }
        br.close();
        return list;
    }

    public static List<String> readFile3(File file) throws Exception {
        List<String> list = Files.readAllLines(file.toPath());
        return list;
    }

    public static void writeLinesToFile1(List<String> lines, File file) throws IOException{
        OutputStream os = new FileOutputStream( file, true ); //以文件追加的方式写入内容
        for (String line: lines) {
            os.write(line.getBytes());
            os.write( "\r\n".getBytes() ); //用硬编码的方式写入换行符
        }
        os.close();
    }

    public static void writeLinesToFile2(List<String> lines, File file) throws Exception{
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true)); //文件追加的方式写入
        for (String line: lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public static void writeLinesToFile3(List<String> lines, File file)throws Exception {
        Files.write(file.toPath(), lines);
    }

    public static void main(String[] args) throws Exception{
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
