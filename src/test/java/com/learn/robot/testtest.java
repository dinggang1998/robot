package com.learn.robot;


import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.io.*;
import java.net.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

//public class DownloadLimiter {
//
//    private static final int BUFFER_SIZE = 1024; // 缓冲区大小
//    private static final int LIMIT_RATE = 50; // 限制下载速度为50KB/s
//
//    public static void main(String[] args) {
//        String url = "http://example.com/file.zip"; // 要下载的文件URL
//        String outputFile = "file.zip"; // 下载后保存的文件名
//        try {
//            URL downloadUrl = new URL(url);
//            HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(5000); // 连接超时时间
//            connection.setReadTimeout(5000); // 读取超时时间
//            int fileSize = connection.getContentLength(); // 文件大小
//            InputStream inputStream = connection.getInputStream();
//            OutputStream outputStream = new FileOutputStream(outputFile);
//            byte[] buffer = new byte[BUFFER_SIZE];
//            int bytesRead;
//            long totalBytesRead = 0;
//            long startTime = System.currentTimeMillis();
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                // 计算当前已下载字节数和时间差，以限制下载速度
//                long now = System.currentTimeMillis();
//                long elapsedTime = now - startTime;
//                if (elapsedTime > 0) {
//                    double rate = (double) totalBytesRead / elapsedTime * 1000; // 当前下载速度，单位：字节/秒
//                    if (rate > LIMIT_RATE * 1024) { // 如果当前下载速度超过了限制速度，则暂停一段时间
//                        try {
//                            Thread.sleep((long) (LIMIT_RATE * 1024 / rate * 1000 - elapsedTime));
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                totalBytesRead += bytesRead;
//                outputStream.write(buffer, 0, bytesRead); // 将读取到的数据写入文件
//                System.out.println("已下载：" + totalBytesRead + "/" + fileSize + "，下载速度：" + rate / 1024 + "KB/s");
//            }
//            outputStream.close();
//            inputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

public class testtest {

    public static void main(String[] args) throws Exception {
//        List<String> list = Arrays.asList("123,213".split(","));
//        System.out.println(list);
//        list.add("1");
//        System.out.println(list);
//        String[] arr3 = {"1","2","3","4"};
//
//        List<String> string1 = Arrays.asList(arr3);
//
//        System.out.println("string1 = " + string1.getClass());
//
//        ArrayList<String> string2 = new ArrayList<>();
//
//        System.out.println("string2 = " + string2.getClass());
//
//
//        List<Integer> list = new ArrayList<>();
//        list.stream().filter(o -> {return o!=2;}).max(Integer::max);
//
//        short s1 = 1;
//        String aaa="1";
//        switch (aaa){
//            case "1":
//                break;
//            default:
//                break;
//        }
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        System.out.println(new UUID(0,0));


    }


}


