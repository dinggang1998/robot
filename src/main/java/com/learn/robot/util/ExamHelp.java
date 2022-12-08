package com.learn.robot.util;


import com.alibaba.acm.shaded.com.google.gson.JsonArray;
import com.alibaba.acm.shaded.com.google.gson.JsonParser;
import com.alibaba.fastjson.JSONObject;
import com.learn.robot.exception.RobotException;
import com.learn.robot.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 这是一个自动考试软件
 * **/
@Slf4j
public class ExamHelp {

    private static String cookie = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36(KHTML, like Gecko) Chrome/98.0" +
            ".4758.82 Safari/537.36 Edg/98.0.1108.51";

    private static String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36(KHTML, like Gecko) Chrome/98.0" +
                                    ".4758.82 Safari/537.36 Edg/98.0.1108.51";


    /**
     *单选题4选一，所以循环一次，参数num为4，
     * 判断题二选一
     * 多选题排列组合之后为11选一，排列组合参照如下算法
     * 主观题一选一
     * flag用以区分，单选多选主观三种题型答题方法不同
     * 单选只需拿answerlist里一个ID，多选拿多个，主观拿answerlist里一个content
     **/
    public static StringBuffer postWithJson(String paperId, String token,int mills) throws SerialException, Exception {
        StringBuffer text=new StringBuffer();
        try{
            JSONObject request = new JSONObject();
            request.put("id", paperId);
            JSONObject data = post(request, "http://221.11.103.120:8101/exam/api/paper/paper/paper-detail", paperId, token, cookie, userAgent);

            if(data.get("userId_dictText")==null){
                throw RobotException.serviceException("500","用户token或paper失效","");
            }

            log.info(data.toString());
            logAndText(text,"====================================欢迎你，"+data.get("userId_dictText"));
            logAndText(text,"====================================你正在参加考试：" +data.get("departId_dictText"));

            //单选题
            getAnswer("radioList", paperId, token, cookie, userAgent, 4,"3",text,mills);
            //判断题
            getAnswer("judgeList", paperId, token, cookie, userAgent, 2,"3",text,mills);
            //多选题
            getAnswer("multiList", paperId, token, cookie, userAgent, 11,"1",text,mills);
            //主观题
            getAnswer( "saqList", paperId, token, cookie, userAgent, 1,"2",text,mills);

        }catch (Exception e){
            e.printStackTrace();
            throw RobotException.serviceExceptionByResponse(new Response("500",e.getMessage()+"(请联系管理员)",""));
        }
        return text;
    }



    public static void getAnswer(String listName, String paperId, String token,
                                 String cookie, String userAgent, int num, String flag,StringBuffer text,int mills) throws Exception {
        for (int j = 0; j < num; j++) {
            JSONObject request = new JSONObject();
            request.put("id", paperId);
            JSONObject data = post(request, "http://221.11.103.120:8101/exam/api/paper/paper/paper-detail", paperId, token, cookie, userAgent);
            if(data==null||data.get(listName)==null){
                throw RobotException.serviceException("500","获取试题失败 ("+listName+")","");
            }
            JsonArray jsonArray = new JsonParser().parse(data.get(listName).toString()).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                if (jsonArray.get(i).getAsJsonObject().get("isRight").toString().equals("false")) {
                    int questionNo=Integer.valueOf(jsonArray.get(i).getAsJsonObject().get("sort").toString())+1;
                    String questionId=jsonArray.get(i).getAsJsonObject().get("quId").toString().replaceAll("\"", "");
                    logAndText(text,"====================================这是第"+questionNo+"题==============================");
                    getAnswerDetail(questionId, paperId, token, cookie, userAgent, j, flag,text,mills);
                }
            }

        }
    }


    public static void getAnswerDetail(String quId, String paperId, String token,
                                       String cookie, String userAgent, int num, String flag,StringBuffer text,int mills) throws Exception {
        JSONObject request = new JSONObject();
        request.put("paperId", paperId);
        request.put("quId", quId);
        JSONObject quDetail = post(request, "http://221.11.103.120:8101/exam/api/paper/paper/qu-detail", paperId, token, cookie, userAgent);
        logAndText(text,"问题:" + quDetail.get("content"));
        JsonArray answerList = new JsonParser().parse(quDetail.get("answerList").toString()).getAsJsonArray();
        List list = new ArrayList();
        String answers="";
        if ("1".equals(flag)) {
            list = getchoose(num, list, answerList,text);
        }else if("2".equals(flag)){
            String content=answerList.get(num).getAsJsonObject().get("content").toString().replaceAll("\"", "");
            answers=content.contains("无")?"不了解" : content;
        }else {
            list.add(answerList.get(num).getAsJsonObject().get("id").toString().replaceAll("\"", ""));
            logAndText(text,"现在开始尝试选择"+change(String.valueOf(num))+answerList.get(num).getAsJsonObject().get("content"));
        }
        fillDetail(paperId, token, cookie, userAgent, quId, list,answers,text,mills);
    }


    public static void fillDetail(String paperId, String token, String cookie, String userAgent, String quId,
                                  List<String> answers,String answer,StringBuffer text,int mills) throws Exception{
        JSONObject request = new JSONObject();
        request.put("paperId", paperId);
        request.put("answer", answer);
        request.put("answers", answers);
        request.put("quId", quId.replaceAll("\"", ""));
        logAndText(text,"开始作答:" + request);
        JSONObject response = post(request, "http://221.11.103.120:8101/exam/api/paper/paper/fill-answer", paperId, token, cookie, userAgent);
        logAndText(text,"作答结果:" + (Integer.valueOf(response.get("statusCode").toString())==200?"成功":"失败"));
        Thread.sleep(mills);
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
        JSONObject data = new JSONObject();
        if (response.get("data") != null) {
            data = JSONObject.parseObject(response.get("data").toString());
        }else{
            data= (JSONObject) JSONObject.toJSON(execute.getStatusLine());
        }
        return data;
    }


    public static List getchoose(int num, List list, JsonArray answerList,StringBuffer text) {
        List l = Arrays.asList("0,1,2,3".split(","));
        List z = new ArrayList();
        String s = "";
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
        logAndText(text,"现在开始尝试选择选择"+change(aList.toString()));
        for (String aString : aList) {
            list.add(answerList.get(Integer.valueOf(aString)).getAsJsonObject().get("id").toString().replaceAll("\"", ""));
        }
        return list;
    }

    static List f(String s, List l, List z) {
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

    public static String change(String s){
        return s.replaceAll("0","A").replaceAll("1","B").replaceAll("2","C").replaceAll("3","D");
    }

    public static void logAndText(StringBuffer text,String msg){
        text.append(msg);
        log.info(msg);
    }

}


