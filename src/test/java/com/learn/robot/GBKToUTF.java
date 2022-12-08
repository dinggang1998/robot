package com.learn.robot;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;

public class GBKToUTF {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "%7B%22body%22%3A%7B%22PROVINCE_CODE%22%3A%2285%22%2C%22EPARCHY_CODE%22%3A%220851" +
                "%22%2C%22DEPART_ID%22%3A%2285a0116%22%2C%22STAFF_ID%22%3A%22AAL51651%22%2C%22PRODUCT_ID%22%3A" +
                "%2290384954%22%2C%22TRADE_TYPE_CODE%22%3A%2210%22%2C%22DEFAULT_TAG%22%3A%220%22%2C" +
                "%22PRODUCT_MODIFY_TAG%22%3A%220%22%7D%7D";//[-62,-73]
//        System.out.println(Arrays.toString(str.getBytes()));
//        System.out.println(str);
        try {
            System.out.println(str.getBytes("utf-8").length);
            String str1 = new String(getUTF8BytesFromGBKString(str), "UTF-8");
            System.out.println(str1);
            System.out.println(str1.getBytes("utf-8").length);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            } else if (m < 2048) {
                utfBytes[k++] = (byte) ((m >> 6) | 0xc0);
                utfBytes[k++] = (byte) (0x80 | ((m & 0x3f)));
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
//            System.out.println(Arrays.toString(tmp));
            return tmp;
        }
        return utfBytes;
    }


    /**
     * 使用common io批量将java编码从GBK转UTF-8
     *
     * @param args
     * @throws Exception
     */
    public static final String[] javastr = { "java" ,"txt"};


    public static void func2() throws Exception {
        // GBK编码格式源码路径
        String srcDirPath = "/Users/dinggang/Downloads/test1";
        // 转为UTF-8编码格式源码路径
        String utf8DirPath = "/Users/dinggang/Downloads/test2";


        // 获取所有java文件
        Collection<File> javaGbkFileCol = FileUtils.listFiles(new File(srcDirPath), javastr, true);

        for (Iterator<File> iterator = javaGbkFileCol.iterator(); iterator.hasNext();)
        {
            File javaGbkFile = iterator.next();
            // UTF8格式文件路径
            String utf8FilePath = utf8DirPath
                    + javaGbkFile.getAbsolutePath().substring(srcDirPath.length());
            String gbk = FileUtils.readLines(javaGbkFile, "GBK").toString();
            // 使用GBK读取数据，然后用UTF-8写入数据
            FileUtils.writeLines(new File(utf8FilePath), "UTF-8",
                    FileUtils.readLines(javaGbkFile, "GBK"));
            System.out.println("转换完成！");
        }

    }


}