package com.learn.robot.util;


import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

public class testsd {
    /**
     * ���ƶ�Ŀ¼�µ�����JavaԴ�ļ��ı����ʽ��GBK�޸�ΪUTF-8
     */
    public static final String[] javastr = {"java", "txt"};

    //    public static void main (String[] args) throws Exception {
    public static void UTF8toGBK(String UTF8src, String toGBKsrc) throws IOException {

        String srcDirPath = "/Users/dinggang/Downloads/test1";
        // 转为UTF-8编码格式源码路径
        String utf8DirPath = "/Users/dinggang/Downloads/test2";


        // ��ȡ����java�ļ�
        Collection<File> javaGbkFileCol = FileUtils.listFiles(new File(srcDirPath), javastr, true);

        for (Iterator<File> iterator = javaGbkFileCol.iterator(); iterator.hasNext(); ) {
            File javaGbkFile = iterator.next();
            // UTF8��ʽ�ļ�·��
            String utf8FilePath = utf8DirPath
                    + javaGbkFile.getAbsolutePath().substring(srcDirPath.length());

            // ʹ��GBK��ȡ���ݣ�Ȼ����UTF-8д������
            FileUtils.writeLines(new File(utf8FilePath), "UTF-8",
                    FileUtils.readLines(javaGbkFile, "GBK"));
            System.out.println("ת����ɣ�");
        }
    }

    /**
     * ���ƶ�Ŀ¼�µ�����JavaԴ�ļ��ı����ʽ��UTF-8�޸�ΪGBK
     */
//    public static void UTF8toGBK(String UTF8src, String toGBKsrc) throws IOException {
    // UTF-8�����ʽԴ��·��
    public static void main(String[] args) throws Exception {

        String srcDirPath = "/Users/dinggang/Downloads/test1";
        // 转为UTF-8编码格式源码路径
        String gbkDirPath = "/Users/dinggang/Downloads/test2";
        // ��ȡ����java�ļ�
        Collection<File> javaUtf8FileCol = FileUtils.listFiles(new File(srcDirPath), javastr, true);

        for (Iterator<File> iterator = javaUtf8FileCol.iterator(); iterator.hasNext(); ) {
            File javaUtf8File = iterator.next();
            // UTF8��ʽ�ļ�·��
            String GBK8FilePath = gbkDirPath
                    + javaUtf8File.getAbsolutePath().substring(srcDirPath.length());

            FileUtils.writeLines(new File(GBK8FilePath), "GBK",
                    FileUtils.readLines(javaUtf8File, "UTF-8"));
            System.out.println("ת����ɣ�");
        }
    }
}