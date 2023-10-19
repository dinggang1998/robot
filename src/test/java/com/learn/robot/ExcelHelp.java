package com.learn.robot;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;

/**
 * Autuor StevenDing
 * CreateTime 2023/10/08 15:10:21
 * 修改storyListPath为导出的需要写自测报告的用户故事文档路径
 * 修改savePath为你要保存的路径
 * 修改下面的用户信息
 */
public class ExcelHelp {

    //自测报告范本路径
    public static String oldExcelPath = "src/main/resources/file/自测报告模版.xlsx";
    //导出需要生成自测报告的excel路径
    public static String storyListPath = "/Users/dinggang/Downloads/2023-10-19一键导出20231019144508.xlsx";
    //导出后的保存路径
    public static String savePath = "/Users/dinggang/Downloads/生成的自测报告/";
    //操作者姓名
    public static String userName = "丁罡";
    //操作者手机号
    public static String userPhone = "15861334359";
    //操作者邮箱
    public static String userEmail = "dinggang@asiainfo.com";
    //需要替换的标题里的字符串（可自行增加）
    public static String[] replaceStr = {
            "-", "—", "\t", "\\.", " ", "（", "）", "、",
            "UI前后台专题", "（补）", "关于", "需求", "3212", "菜单",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "0"
    };
    //需要保护的标题里的字符串（可自行增加）(2.0可能导致文件上传格式不对，仅测试时使用)
    public static String[] saveStr = {
            "2.0菜单", "2.0"
    };
    //需要保护的标题里的字符串替换后的字符串
    public static String[] lastSaveStr = {
            "2-0菜单", "2-0"
    };


//    //操作者姓名
//    public static String userName = "顾明辉";
//    //操作者手机号
//    public static String userPhone = "15251812986";
//    //操作者邮箱
//    public static String userEmail = "gumh@asiainfo.com";


    public static void main(String[] args) throws Exception {

        List<ExcelDemo> excelDemoList = new ArrayList<ExcelDemo>();
        Sheet sheet = readExcel(storyListPath).getSheetAt(0);

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            // 循环读取每一个格
            excelDemoList.add(new ExcelDemo(getCell(sheet, i, 1, true), getCell(sheet, i, 3)));
        }

        //把目录先删除再建
        File desFilePath = new File(savePath);
        deleteDirectory(desFilePath);
        desFilePath.mkdirs();

        if (!CollectionUtils.isEmpty(excelDemoList)) {
            for (ExcelDemo excelDemo : excelDemoList) {
                //定义自测报告名称
                String newTitle = dealString(excelDemo.getStoryTitle(), Arrays.asList(replaceStr),
                        Arrays.asList(saveStr), Arrays.asList(lastSaveStr));
                System.out.println(newTitle);

                String newExcelName = excelDemo.getStoryId() + "+" + userName + "+" + newTitle + "+开发测试说明.xlsx";
                //复制生成自测报告
                copyFile(oldExcelPath, savePath + newExcelName);
                //对自测报告内容进行修改
                Workbook workbook = readExcel(savePath + newExcelName);
                Sheet newSheet = workbook.getSheetAt(1);
                changeCell(newSheet, 0, 3, userName);
                changeCell(newSheet, 1, 8, userEmail);
                changeCell(newSheet, 1, 11, userPhone);
                changeCell(newSheet, 2, 1, newTitle);
                changeCell(newSheet, 4, 1, newTitle);
                changeCell(newSheet, 7, 1, newTitle);
                changeCell(newSheet, 9, 1, newTitle);

                FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath + newExcelName));
                workbook.write(fileOutputStream); // 将Workbook写入文件
                fileOutputStream.close();

                System.out.println(newExcelName + "------执行结束！");

                //将自测报告进行上传(该功能未开发完善)
//                upload(savePath + newExcelName);
            }
        }
    }

    public static Workbook readExcel(String path) {
        Workbook workbook = null;
        try {
            InputStream inputStream = new FileInputStream(path);
            switch (path.substring(path.lastIndexOf(".") + 1)) {
                case "xls":
                    workbook = new HSSFWorkbook(inputStream);
                    break;
                case "xlsx":
                    workbook = new XSSFWorkbook(inputStream);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    private static void copyFile(String sourceFileNameStr, String desFileNameStr) {
        File srcFile = new File(sourceFileNameStr);
        File desFile = new File(desFileNameStr);
        try {
            copyFileDetail(srcFile, desFile);
        } catch (IOException e) {
            System.err.println("复制失败:" + desFile + e.getMessage().substring(e.getMessage().indexOf("(")));
        }
    }


    public static void copyFileDetail(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }

    public static String getCell(Sheet sheet, int RowNum, int CellNum) {
        Row row = sheet.getRow(RowNum);
        Cell cell = row.getCell(CellNum);
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    public static String getCell(Sheet sheet, int RowNum, int CellNum, boolean isNum) {
        Row row = sheet.getRow(RowNum);
        Cell cell = row.getCell(CellNum);
        cell.setCellType(CellType.STRING);
        return !isNum ? cell.getStringCellValue()
                : cell.getStringCellValue().replaceAll("\\.0", "");
    }

    public static void changeCell(Sheet sheet, int RowNum, int CellNum, String context) {
        Row row = sheet.getRow(RowNum);
        Cell cell = row.getCell(CellNum);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(context);
    }

    public static boolean deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
        return directory.delete();
    }

    public static void upload(String path) throws Exception {
        InputStream inputStream = new FileInputStream(path);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("file", inputStream);
        jsonObject.put("version", "1");
        post(jsonObject, "https://uims.tg.unicom.local/pm/fileController/fileUploadInfo", "", "");
    }


    public static JSONObject post(JSONObject request, String url, String token, String cookie) throws IOException,
            Exception {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary8jjfAm3qDPK7U4XB");
        httpPost.addHeader("Cookie", cookie);
        httpPost.setEntity(new StringEntity(request.toJSONString(), "utf-8"));
        HttpResponse response = httpClient.execute(httpPost);
        String res = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(res);
        if (res.contains("<h")) {
            System.out.println("服务异常；" + res);
            return new JSONObject();
        }
        JSONObject resJson = JSONObject.parseObject(res);
        JSONObject resState = (JSONObject) JSONObject.toJSON(response.getStatusLine());
        if (resState != null && "200".equals(resState.getString("statusCode"))
                && "1".equals(resJson.get("ok").toString())) {
            JSONArray list = resJson.getJSONArray("statuses");
            return list.getJSONObject(0);
        } else {
            return new JSONObject();
        }
    }

    public static String dealString(String newTitle, List<String> replaceStr, List<String> saveStr,
                                    List<String> lastSaveStr) {
        //用于反解密保护的字符串（不能修改）
        List<String> forSaveStrList = new LinkedList<>();
        //加密保护字符串
        for (int i = 0; i < Arrays.asList(saveStr).size(); i++) {
            String forSaveStr = generateRandomLowercase(6);
            forSaveStrList.add(forSaveStr);
            newTitle = newTitle.replaceAll(saveStr.get(i), forSaveStr);
        }
        for (String str : replaceStr) {
            newTitle = newTitle.replaceAll(str, "");
        }
        //反解密保护字符串
        for (int i = 0; i < Arrays.asList(lastSaveStr).size(); i++) {
            newTitle = newTitle.replaceAll(forSaveStrList.get(i), lastSaveStr.get(i));
        }
        return newTitle;
    }

    public static String generateRandomLowercase(int length) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    @Data
    public static class ExcelDemo {

        private String storyId;
        private String storyTitle;

        public ExcelDemo(String storyId, String storyTitle) {
            this.storyId = storyId;
            this.storyTitle = storyTitle;
        }
    }
}

