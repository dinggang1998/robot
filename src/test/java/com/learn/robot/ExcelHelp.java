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
import java.util.ArrayList;
import java.util.List;

public class ExcelHelp {

    //自测报告范本路径
    public static String oldExcelPath = "/Users/dinggang/Downloads/test11.xls";
    //导出需要生成自测报告的excel路径
    public static String storyListPath = "/Users/dinggang/Downloads/test.xls";
    //导出后的保存路径
    public static String savePath = "/Users/dinggang/Downloads/etst/";
    //操作者姓名
    public static String userName = "丁罡";


    public static void main(String[] args) throws Exception {

        List<ExcelDemo> excelDemoList = new ArrayList<ExcelDemo>();
        Sheet sheet = readExcel(storyListPath, 0);

        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            // 循环读取每一个格
            Row row = sheet.getRow(i);
            Cell cellId = row.getCell(1);
            cellId.setCellType(CellType.STRING);
            String id = cellId.getStringCellValue();

            Cell cellTitle = row.getCell(3);
            cellTitle.setCellType(CellType.STRING);
            String title = cellTitle.getStringCellValue();

            ExcelDemo excelDemo = new ExcelDemo();
            excelDemo.setStoryId(id);
            excelDemo.setStoryTitle(title);
            excelDemoList.add(excelDemo);
        }
        if (!CollectionUtils.isEmpty(excelDemoList)) {
            for (ExcelDemo excelDemo : excelDemoList) {

                String newTitle = excelDemo.getStoryTitle().replaceAll("—", "").replaceAll("UI前后台专题", "")
                        .replace("-", "").replaceAll("\t", "").replaceAll("\\.", "-");
                String newExcelName = excelDemo.getStoryId() + "+" + userName + "+" + newTitle +
                        "+开发测试说明.xls";
                copyFile(oldExcelPath, savePath + newExcelName);

                String path = savePath + newExcelName;
                InputStream inputStream = new FileInputStream(path);
                Workbook workbook = null;
                if (path.substring(Integer.valueOf(path.lastIndexOf(".")) + 1).equals("xls")) {
                    workbook = new HSSFWorkbook(inputStream);
                } else if (path.substring(Integer.valueOf(path.lastIndexOf(".")) + 1).equals("xlsx")) {
                    workbook = new XSSFWorkbook(inputStream);
                }
                Sheet newSheet = workbook.getSheetAt(1);

                Row row2 = newSheet.getRow(2);
                Cell cell2 = row2.getCell(1);
                cell2.setCellType(CellType.STRING);
                cell2.setCellValue(newTitle);

                Row row4 = newSheet.getRow(4);
                Cell cell4 = row4.getCell(1);
                cell4.setCellType(CellType.STRING);
                cell4.setCellValue(newTitle);

                Row row7 = newSheet.getRow(7);
                Cell cell7 = row7.getCell(1);
                cell7.setCellType(CellType.STRING);
                cell7.setCellValue(newTitle);

                Row row9 = newSheet.getRow(9);
                Cell cell9 = row9.getCell(1);
                cell9.setCellType(CellType.STRING);
                cell9.setCellValue(newTitle);

                FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
                workbook.write(fileOutputStream); // 将Workbook写入文件
                fileOutputStream.close();

                System.out.println("执行结束！");

            }
        }

//        InputStream inputStream = new FileInputStream("/Users/dinggang/Downloads/etst/338338+丁罡+数据权限管理选择域权及增加授权功能+开发测试说明.xls");
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("file",inputStream);
//        jsonObject.put("version","1");
//        post(jsonObject,"https://uims.tg.unicom.local/pm/fileController/fileUploadInfo","","uuid=89100; canOpen=1; ttUserId=c0ee96d074574d309f6a35830eb7aa88; JSESSIONID=5382CBF094F18A281B4AC189E650CA94; defaultAccount=true; sessionId=40c5dc7b32a34d53bbefae94d03; jeesite.session.id=OTE0ZmMyN2ItODdmYi00NDdmLTk2Y2UtNTRiZGFjMWM4M2I1; accessToken=eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc1Jvb3QiOiIwIiwiYWNjb3VudE5hbWUiOiJtYWw2NS03IiwicmVmcmVzaFRpbWUiOjE4MCwiaXNzIjoiY3Vzb2Z0d2FyZSIsIm1vYmlsZSI6IjEzMDE2NTQzMDIwIiwidXNlck5hbWUiOiJkaW5nZ2FuZyIsImFjY2Vzc1Rva2VuIjoiY2JhMDc2NjRmOWVlNDYzZDhhYzU1NjY3NjgxYjA5MmUiLCJ1c2VySUQiOiI2Mzc0ODM5NTgxMjgiLCJ0dFVzZXJJZCI6ImMwZWU5NmQwNzQ1NzRkMzA5ZjZhMzU4MzBlYjdhYTg4IiwiYWNjb3VudElEIjoiMjA0MTE5MjQ4NDQxIiwiaXNFbmFibGVDb25zb2xlIjoiMSIsImlkIjoiYzBlZTk2ZDA3NDU3NGQzMDlmNmEzNTgzMGViN2FhODgiLCJleHAiOjE2OTY3NTE4NjQsImVtYWlsIjoiZGluZ2dhbmdAYXNpYWluZm8uY29tIiwiaXNFbmFibGVQcm9ncmFtIjoiMSJ9.HRcittfTBUkFDIULg6-NbAJVnYNYFQORdPBqA-VA9cczL73SAFpfWl11TkcYXkF385zBSJvbC9BMbEDMiNlZMg; defaultApp=true; lcdpAccessToken=7ff329cc-957e-4e8d-a0ef-f42530973edf");

    }

    public static Sheet readExcel(String path, int num) {
        Sheet sheet = null;
        try {
            InputStream inputStream = new FileInputStream(path);
            Workbook workbook = null;
            if (path.substring(Integer.valueOf(path.lastIndexOf(".")) + 1).equals("xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (path.substring(Integer.valueOf(path.lastIndexOf(".")) + 1).equals("xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            }
            sheet = workbook.getSheetAt(num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sheet;
    }

    private static void copyFile(String sourceFileNameStr, String desFileNameStr) {
        File srcFile = new File(sourceFileNameStr);
        File desFile = new File(desFileNameStr);
        try {
            copyFileDetail(srcFile, desFile);
//            System.out.println("复制完成:" + desFile);
        } catch (IOException e) {
//            System.err.println("复制失败:" + desFile + e.getMessage().substring(e.getMessage().indexOf("(")));
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

    public static JSONObject post(JSONObject request, String url, String token, String cookie) throws IOException, Exception {
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


    @Data
    public static class ExcelDemo {
        private String storyId;
        private String storyTitle;
    }

}

