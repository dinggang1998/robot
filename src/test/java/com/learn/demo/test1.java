package com.learn.demo;


import com.alibaba.acm.shaded.com.google.gson.JsonArray;
import com.alibaba.acm.shaded.com.google.gson.JsonParser;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;


public class test1 {

    public static void main(String[] args) {
        postWithJson(
                "1600059347520585730",
                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NzA0MDUyMjgsInVzZXJuYW1lIjoibHZsdmx2azEifQ" +
                        ".5esltDLW9luCPAYAl84AYSW-Tpiv-whRHLixB_Hvc9Q",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0" +
                        ".4758.82 Safari/537.36 Edg/98.0.1108.51",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36(KHTML, like Gecko) Chrome/98.0" +
                        ".4758.82 Safari/537.36 Edg/98.0.1108.51"
        );
    }

    public static String postWithJson(String paperId, String token, String cookie, String userAgent) {
        try {
            JSONObject request = new JSONObject();
            request.put("id", paperId);
            JSONObject data = post(request, "http://221.11.103.120:8101/exam/api/paper/paper/paper-detail", paperId,
                    token, cookie, userAgent);

            //单选题
//            getAnswer(data, "radioList", paperId, token, cookie, userAgent, 4);

            //判断题
//            getAnswer(data, "judgeList", paperId, token, cookie, userAgent, 2);

            //多选题
            getAnswer(data, "multiList", paperId, token, cookie, userAgent, 11);


//            //主观题
//            getAnswer(data, "saqList", paperId, token, cookie, userAgent, 1);
//
//            List<Question> questionList = new ArrayList<>();
//            JsonArray jsonArray = new JsonParser().parse(data.get(listName).toString()).getAsJsonArray();
//
//            for (int i = 0; i < jsonArray.size(); i++) {
//                if (jsonArray.get(i).getAsJsonObject().get("isRight").toString().equals("false")) {
//                    post(paperId, token, cookie, jsonArray.get(i).getAsJsonObject().get("quId").toString(), num, 2);
//                }
//            }
//
//
//            JsonArray radioList = new JsonParser().parse(data.get("radioList").toString()).getAsJsonArray();
//            if (radioList != null) {
//                for (int i = 0; i < radioList.size(); i++) {
//                    if (radioList.get(i).getAsJsonObject().get("isRight").toString().equals("false")) {
//                        getAnswer(paperid, token, cookie, radioList.get(i).getAsJsonObject().get("quId").toString(),
//                                num, 1);
//                    }
//                }
//            }
////多选题
//            JsonArray multiList = new JsonParser().parse(data.get("multiList").toString()).getAsJsonArray();
//            if (multiList != null) {
//                for (int i = 0; i < multiList.size(); i++) {
//                    if (multiList.get(i).getAsJsonObject().get("isRight").toString().equals("false")) {
//                        post(paperid, token, cookie, multiList.get(i).getAsJsonObject().get("quId").toString(),
//                        num, 2);
//                    }
//                }
//            }
////判断题
//            JsonArray judgeList = new JsonParser().parse(data.get("judgeList").toString()).getAsJsonArray();
//            if (judgeList != null) {
//                for (int i = 0; i < judgeList.size(); i++) {
//                    if (judgeList.get(i).getAsJsonObject().get("isRight").toString().equals("false")) {
//                        getAnswer(paperid, token, cookie, judgeList.get(i).getAsJsonObject().get("quId").toString(),
//                                num, 1);
//                    }
//                }
//            }
//
//            //主观题
//            JsonArray saqList = new JsonParser().parse(data.get("saqList").toString()).getAsJsonArray();
//            if (saqList != null) {
//                for (int i = 0; i < saqList.size(); i++) {
//                    if (saqList.get(i).getAsJsonObject().get("isRight").toString().equals("false")) {
//                        getAnswer(paperid, token, cookie, saqList.get(i).getAsJsonObject().get("quId").toString(),
//                                num, 1);
//                    }
//                }
//            }
//
//
//            getdetail(paperid, token, cookie, 0, execute, httpClient, httpPost);
//            getdetail(paperid, token, cookie, 1, execute, httpClient, httpPost);
//            getdetail(paperid, token, cookie, 2, execute, httpClient, httpPost);
//            getdetail(paperid, token, cookie, 3, execute, httpClient, httpPost);
//            getdetail(paperid, token, cookie, 4, execute, httpClient, httpPost);
//            getdetail(paperid, token, cookie, 5, execute, httpClient, httpPost);
//            getdetail(paperid, token, cookie, 6, execute, httpClient, httpPost);
//            getdetail(paperid, token, cookie, 7, execute, httpClient, httpPost);
//            getdetail(paperid, token, cookie, 8, execute, httpClient, httpPost);
//            getdetail(paperid, token, cookie, 9, execute, httpClient, httpPost);
//            getdetail(paperid, token, cookie, 10, execute, httpClient, httpPost);

        } catch (IOException e) {
            System.out.println("消息发送失败！");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("签名获取失败");
            e.printStackTrace();
        }
        return null;
    }


//    public static void getdetail(String paperid, String token, String cookie, int num,
//                                 HttpClient httpClient, HttpPost httpPost) throws IOException {
//
//
//        HttpResponse execute = httpClient.execute(httpPost);
//        String res = EntityUtils.toString(execute.getEntity(), "utf-8");
//        JSONObject jsonObject1 = JSONObject.parseObject(res);
//        JSONObject jsonObject2 = JSONObject.parseObject(jsonObject1.get("data").toString());
//        JsonArray multiList = new JsonParser().parse(jsonObject2.get("multiList").toString()).getAsJsonArray();
//        JsonArray radioList = new JsonParser().parse(jsonObject2.get("radioList").toString()).getAsJsonArray();
//
//        for(
//    int i = 0; i<multiList.size();i++)
//
//    {
//        System.out.println("题号：" + multiList.get(i).getAsJsonObject().get("sort") + "编号：" + multiList.get(i)
//                .getAsJsonObject().get("quId"));
//        if (multiList.get(i).getAsJsonObject().get("isRight").toString().equals("false")) {
//            post(paperid, token, cookie, multiList.get(i).getAsJsonObject().get("quId").toString(), num, 2);
//        }
//    }
//        for (int i = 0; i < radioList.size(); i++) {
//            System.out.println("题号：" + radioList.get(i).getAsJsonObject().get("sort") + "编号：" + radioList.get(i)
//            .getAsJsonObject().get("quId"));
//            System.out.println("222222222" + radioList.get(i).getAsJsonObject().get("isRight").toString());
//            System.out.println(radioList.get(i).getAsJsonObject().get("isRight").toString().equals("false"));
//
//            if (radioList.get(i).getAsJsonObject().get("isRight").toString().equals("false")) {
//                post(paperid, token, cookie, radioList.get(i).getAsJsonObject().get("quId").toString(), num, 1);
//            }
//        }
//
//    }


    public static void getAnswer(JSONObject data, String listName, String paperId, String token,
                                 String cookie, String userAgent, int num) throws IOException {
        JsonArray jsonArray = new JsonParser().parse(data.get(listName).toString()).getAsJsonArray();
        boolean flag=listName.equals("multiList")?false:true;
        for (int j = 0; j < num; j++) {
            for (int i = 0; i < jsonArray.size(); i++) {
                if (jsonArray.get(i).getAsJsonObject().get("isRight").toString().equals("false")) {
                    getAnswerDetail(jsonArray.get(i).getAsJsonObject().get("quId").toString().replaceAll("\"", ""),
                            paperId, token, cookie, userAgent, j, flag);
                }
            }
        }

    }


    public static void getAnswerDetail(String quId, String paperId, String token,
                                       String cookie, String userAgent, int num, boolean flag) throws IOException {

        JSONObject request = new JSONObject();
        request.put("paperId", paperId);
        request.put("quId", quId);
        JSONObject quDetail = post(request, "http://221.11.103.120:8101/exam/api/paper/paper/qu-detail",
                paperId, token, cookie, userAgent);
        JsonArray answerList = new JsonParser().parse(quDetail.get("answerList").toString()).getAsJsonArray();
        ArrayList list = new ArrayList();
        if (flag == false) {
            list = getchoose(num, list, answerList);
        } else {
            list.add(answerList.get(num).getAsJsonObject().get("id").toString().replaceAll("\"", ""));
        }
        fillDetail(paperId, token, cookie, userAgent, quId, list);
    }


    public static void fillDetail(String paperId, String token, String cookie, String userAgent, String quId,
                                  List<String> answers) throws
            IOException {
        JSONObject request = new JSONObject();
        request.put("paperId", paperId);
        request.put("answers", answers);
        request.put("answer", "");
        request.put("quId", quId.replaceAll("\"", ""));
        JSONObject response = post(request, "http://221.11.103.120:8101/exam/api/paper/paper/fill-answer", paperId,
                token, cookie, userAgent);
        System.out.println("==================================开始作答:" + request + "===========================================");
    }


    public static JSONObject post(JSONObject request, String url, String paperId, String token, String cookie,
                                  String userAgent) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("token", token);
        httpPost.addHeader("User-Agent", userAgent);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Cookie", cookie);
        httpPost.setEntity(new StringEntity(request.toJSONString(), "utf-8"));
        HttpResponse execute = httpClient.execute(httpPost);
        String res = EntityUtils.toString(execute.getEntity(), "utf-8");
        JSONObject response = JSONObject.parseObject(res);

        System.out.println(response);
        JSONObject data = new JSONObject();
        if (response.get("data") != null) {
            data = JSONObject.parseObject(response.get("data").toString());
        }
        return data;
    }


    public static ArrayList getchoose(int num, ArrayList list, JsonArray answerList) {
        ArrayList l = new ArrayList();
        ArrayList z = new ArrayList();

        String s = "";
        l.add("0");
        l.add("1");
        l.add("2");
        l.add("3");

        z = f(s, l, z);
        String[] aa = new String[20];
        int cc = 0;

        for (int i = 0; i < z.size(); i++) {
            if (z.get(i).toString().length() != 1) {
                aa[cc] = z.get(i).toString();
                cc++;
            }
        }
        List<String> aList = Arrays.asList(aa[num].split(""));
        for (String aString : aList) {
            list.add(answerList.get(Integer.valueOf(aString)).getAsJsonObject().get("id").toString().replaceAll("\"",
                    ""));
        }
        return list;
    }

    static ArrayList f(String s, ArrayList l, ArrayList z) {
        if (s.isEmpty()) {
            for (int i = 0; i < l.size() - 1; i++) {
                z.add(s + l.get(i + 1));
                if (i == l.size() - 1) break;
                f(s + l.get(i), l, z);
            }
        } else {
            char[] c = new char[1];
            c[0] = s.charAt(s.length() - 1);
            String str = new String(c);
            for (int i = l.lastIndexOf(str); i <= l.size() - 2; i++) {
                z.add(s + l.get(i + 1));
                if (i == l.size() - 2) break;
                f(s + l.get(i + 1), l, z);
            }
        }
        return z;
    }

//    @Data
//    public static class Question {
//        private String quId;
//        private int sort;
//        private LinkedList<String> answers;
//    }
}


