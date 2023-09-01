package com.learn.robot;


import com.alibaba.acm.shaded.com.google.gson.JsonArray;
import com.alibaba.acm.shaded.com.google.gson.JsonElement;
import com.alibaba.acm.shaded.com.google.gson.JsonParser;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * 这是一个自动微博设置工具
 * 5463014783
 * 6491843419
 * 5612253786
 **/
public class WeiboHelp {

    public static void main(String[] args) throws Exception {
//        changeType(
//                "5612253786",
//                "86ZrOA9umOtxvqqGkHdzDapu",
//                "XSRF-TOKEN=86ZrOA9umOtxvqqGkHdzDapu; _s_tentry=weibo.com; Apache=811696460495.4924.1693537780253; " +
//                        "SINAGLOBAL=811696460495.4924.1693537780253; ULV=1693537780331:1:1:1:811696460495.4924" +
//                        ".1693537780253:; " +
//                        "SUB=_2A25J9RITDeThGeFL41MS9SfNwz6IHXVqgwTbrDV8PUNbmtANLXiskW9NfbaweFE4aemVHb" +
//                        "-LH2C8hcqh_8u2mgNE; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5GPa58Qr8QzZ56s0U7h9Cd5JpX5KzhUgL" +
//                        ".FoMf1h20SK.p1hz2dJLoIEXLxKqL1-eL1hnLxKBLB.2LB--LxKBLBonL12BLxK-LBKBLB-2LxK-L1hML1h.t; " +
//                        "ALF=1725076930; SSOLoginState=1693540931; " +
//                        "WBPSESS" +
//                        "=Dt2hbAUaXfkVprjyrAZT_MVwEWKOzq_1VbCkk1pWIw2HmyTOoag9k9xjf1KWMfaOwegJcfLVAuiOIwD683J5HupZiK" +
//                        "-pgK5h2MM8ouW8v4EHpxPsLNCvRUzocV5c4bwql3Dq4oGcUN_-b9PehjN8ebADQ_lKyZ0X_XgsP1H6I0A=",
//                "2",false,""
//        );
//        getMessage(
//                "100057581359182",
//                "86ZrOA9umOtxvqqGkHdzDapu",
//                "XSRF-TOKEN=86ZrOA9umOtxvqqGkHdzDapu; _s_tentry=weibo.com; Apache=811696460495.4924.1693537780253; " +
//                        "SINAGLOBAL=811696460495.4924.1693537780253; ULV=1693537780331:1:1:1:811696460495.4924" +
//                        ".1693537780253:; " +
//                        "SUB=_2A25J9RITDeThGeFL41MS9SfNwz6IHXVqgwTbrDV8PUNbmtANLXiskW9NfbaweFE4aemVHb" +
//                        "-LH2C8hcqh_8u2mgNE; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5GPa58Qr8QzZ56s0U7h9Cd5JpX5KzhUgL" +
//                        ".FoMf1h20SK.p1hz2dJLoIEXLxKqL1-eL1hnLxKBLB.2LB--LxKBLBonL12BLxK-LBKBLB-2LxK-L1hML1h.t; " +
//                        "ALF=1725076930; SSOLoginState=1693540931; " +
//                        "WBPSESS" +
//                        "=Dt2hbAUaXfkVprjyrAZT_MVwEWKOzq_1VbCkk1pWIw2HmyTOoag9k9xjf1KWMfaOwegJcfLVAuiOIwD683J5HupZiK" +
//                        "-pgK5h2MM8ouW8v4EHpxPsLNCvRUzocV5c4bwql3Dq4oGcUN_-b9PehjN8ebADQ_lKyZ0X_XgsP1H6I0A="
//        );
        getAllMessage(
                "5840431155",
                "86ZrOA9umOtxvqqGkHdzDapu",
                "XSRF-TOKEN=86ZrOA9umOtxvqqGkHdzDapu; _s_tentry=weibo.com; Apache=811696460495.4924.1693537780253; " +
                        "SINAGLOBAL=811696460495.4924.1693537780253; ULV=1693537780331:1:1:1:811696460495.4924" +
                        ".1693537780253:; " +
                        "SUB=_2A25J9RITDeThGeFL41MS9SfNwz6IHXVqgwTbrDV8PUNbmtANLXiskW9NfbaweFE4aemVHb" +
                        "-LH2C8hcqh_8u2mgNE; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5GPa58Qr8QzZ56s0U7h9Cd5JpX5KzhUgL" +
                        ".FoMf1h20SK.p1hz2dJLoIEXLxKqL1-eL1hnLxKBLB.2LB--LxKBLBonL12BLxK-LBKBLB-2LxK-L1hML1h.t; " +
                        "ALF=1725076930; SSOLoginState=1693540931; " +
                        "WBPSESS" +
                        "=Dt2hbAUaXfkVprjyrAZT_MVwEWKOzq_1VbCkk1pWIw2HmyTOoag9k9xjf1KWMfaOwegJcfLVAuiOIwD683J5HupZiK" +
                        "-pgK5h2MM8ouW8v4EHpxPsLNCvRUzocV5c4bwql3Dq4oGcUN_-b9PehjN8ebADQ_lKyZ0X_XgsP1H6I0A="
        );

    }


    /**
     *修改自己微博的权限
     * type (0公开，1隐藏，2好友圈可见)
     * appoint (true，指定的改某种类型，false，所有的都改)
     * originalType 需要处理的原来的微博状态，只有appoint为true才需要该参数
     **/
    public static void changeType(String uid, String token, String cookie, String type,boolean appoint,String originalType) throws Exception {
        JSONObject userInfo = get("https://weibo.com/ajax/profile/info?uid=" + uid, token, cookie);
        if (userInfo.get("user") != null) {
            String totalNum = JSONObject.parseObject(userInfo.get("user").toString()).get("statuses_count").toString();
            String userName = JSONObject.parseObject(userInfo.get("user").toString()).get("screen_name").toString();
            int page = (Integer.valueOf(totalNum) / 27) + 1;
            system(new String[]{"当前用户为：", userName});
            system(new String[]{"微博总数量为：", totalNum});
            system(new String[]{"微博页数：", String.valueOf(page)});
            Map weiboMap = new HashMap();
            List<String> midList = new ArrayList<>();
            for (int i = 1; i <= page; i++) {
                JSONObject response = get("https://weibo.com/ajax/statuses/mymblog?uid=" + uid + "&page=" + i + "&feature=0", token, cookie);
                JsonArray weiboMidList = new JsonParser().parse(response.get("list").toString()).getAsJsonArray();
                for (int j = 0; j < weiboMidList.size(); j++) {
                    String mid = JSONObject.parseObject(String.valueOf(weiboMidList.get(j))).get("mid").toString();
                    String visibleType = ((JSONObject) JSONObject.parseObject(String.valueOf(weiboMidList.get(j))).get("visible")).get("type").toString();
                    if(!appoint){
                        //只处理需要处理的微博
                        if (!type.equals(visibleType)) {
                            midList.add(mid);
                            weiboMap.put(mid, JSONObject.parseObject(String.valueOf(weiboMidList.get(j))).get("text").toString());
                        }
                    }else{
                        if (originalType.equals(visibleType)) {
                            midList.add(mid);
                            weiboMap.put(mid, JSONObject.parseObject(String.valueOf(weiboMidList.get(j))).get("text").toString());
                        }
                    }

                }
            }
            system(new String[]{"需要处理", String.valueOf(midList.size()),"条微博"});
            if (CollectionUtils.isNotEmpty(midList)) {
                for (String mid : midList) {
                    JSONObject request = new JSONObject();
                    request.put("ids", mid);
                    request.put("visible", type);
                    JSONObject data = post(request, "https://weibo.com/ajax/statuses/modifyVisible", token, cookie);
                    if (data != null && data.get("mid") != null) {
                        system(new String[]{"微博：",data.get("text").toString(),"已设置为",((JSONObject)data.get("title")).get("text").toString()});
                    } else {
                        system(new String[]{"微博：",weiboMap.get(mid).toString(),"","设置失败","mid：",mid});
                    }
                    Thread.sleep(1000);
                }
            }
            System.out.println("已经设置完成咯！");
        } else {
            System.err.println("执行异常！");
        }
    }

    /**
     *获取某固定用户的微博内容
     **/
    public static void getAllMessage(String uid, String token, String cookie) throws Exception {
        JSONObject userInfo = get("https://weibo.com/ajax/profile/info?uid=" + uid, token, cookie);
        if (userInfo.get("user") != null) {
            String totalNum = JSONObject.parseObject(userInfo.get("user").toString()).get("statuses_count").toString();
            String userName = JSONObject.parseObject(userInfo.get("user").toString()).get("screen_name").toString();
            int page = (Integer.valueOf(totalNum) / 27) + 1;
            system(new String[]{"当前用户为：", userName});
            system(new String[]{"微博总数量为：", totalNum});
            system(new String[]{"微博页数：", String.valueOf(page)});
            for (int i = 1; i <= page; i++) {
                JSONObject response = get("https://weibo.com/ajax/statuses/mymblog?uid=" + uid + "&page=" + i + "&feature=0", token, cookie);
                JsonArray weiboMidlist = new JsonParser().parse(response.get("list").toString()).getAsJsonArray();
                for (int j = 0; j < weiboMidlist.size(); j++) {
                    String regionName = JSONObject.parseObject(String.valueOf(weiboMidlist.get(j))).get("region_name") != null ?
                                    JSONObject.parseObject(String.valueOf(weiboMidlist.get(j))).get("region_name").toString() : "发布于未知";
                    String createdAt = JSONObject.parseObject(String.valueOf(weiboMidlist.get(j))).get("created_at").toString();
//                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:m:ss");
//                    Date date = simpleDateFormat.parse(createdAt);
//                    System.out.println(date);
                    String textRaw = JSONObject.parseObject(String.valueOf(weiboMidlist.get(j))).get("text_raw").toString();
                    String attitudesCount = JSONObject.parseObject(String.valueOf(weiboMidlist.get(j))).get("attitudes_count").toString();
                    String commentsCount = JSONObject.parseObject(String.valueOf(weiboMidlist.get(j))).get("comments_count").toString();
                    String source = JSONObject.parseObject(String.valueOf(weiboMidlist.get(j))).get("source").toString();
                    source=source.contains("<")?source.substring(source.indexOf(">") + 1,source.indexOf("</")):source;
                    https://weibo.com/ajax/statuses/buildComments?is_reload=1&id=4821943373269620&is_show_bulletin=3&is_mix=0&count=20&type=feed&uid=5840431155&fetch_level=0&locale=zh-CN

                    if (null == JSONObject.parseObject(String.valueOf(weiboMidlist.get(j))).get("title")) {
                        System.out.println("===================================第"+(((i-1)*27)+j+1)+"条微博========================================");
                        system(new String[]{"发布时间：", createdAt});
                        system(new String[]{"发布地：", regionName.replaceAll("发布于", ""),",来自于：",source});
                        system(new String[]{"有", commentsCount,"人评论，有",attitudesCount,"人点赞"});
                        system(new String[]{"微博内容：", textRaw.replaceAll("\\n", "")});
                    }
                }
            }
            System.out.println("获取结束啦！");
        } else {
            System.err.println("执行异常！");
        }
    }

    /**
     *获取悄悄关注的列表页内容
     **/
    public static void getMessage(String listId, String token, String cookie) throws Exception {
        JsonArray messageInfos = getList("https://weibo.com/ajax/feed/groupstimeline?list_id=" + listId + "&refresh=4&fast_refresh=1&count=25", token, cookie);
        if (messageInfos != null && messageInfos.size() > 0) {
            for (JsonElement messageInfo : messageInfos) {
                String screenName = JSONObject.parseObject(JSONObject.parseObject(messageInfo.toString()).get("user").toString()).get("screen_name").toString();
                String createdAt = JSONObject.parseObject(messageInfo.toString()).get("created_at").toString();
                String textRaw = JSONObject.parseObject(messageInfo.toString()).get("text_raw").toString();
                System.out.println("===========================================================================");
                system(new String[]{"用户名", screenName});
                system(new String[]{"发布时间", createdAt});
                system(new String[]{"微博内容", textRaw});
            }
        } else {
            System.err.println("执行异常！");
        }
    }

    /**
    *很显然这是一个get请求用来获取返回json
    **/
    public static JSONObject get(String url, String token, String cookie) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-Type", "application/json");
        httpGet.addHeader("Cookie", cookie);
        httpGet.addHeader("X-Xsrf-Token", token);
        JSONObject data = new JSONObject();
        try {
            // 发送请求并获取响应
            HttpResponse response = httpClient.execute(httpGet);
            String res = EntityUtils.toString(response.getEntity(), "utf-8");
            if (res.contains("<h")) {
                System.out.println("服务异常；" + res);
                return new JSONObject();
            }
            JSONObject responses = JSONObject.parseObject(res);
            if (responses.get("data") != null) {
                data = JSONObject.parseObject(responses.get("data").toString());
            }
            if (responses.get("statuses") != null) {
                JsonArray list = new JsonParser().parse(responses.get("statuses").toString()).getAsJsonArray();
                data = JSONObject.parseObject(String.valueOf(list.get(0)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return data;
    }

    /**
     *很显然这是一个get请求用来获取返回json数组
     **/
    public static JsonArray getList(String url, String token, String cookie) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-Type", "application/json");
        httpGet.addHeader("Cookie", cookie);
        httpGet.addHeader("X-Xsrf-Token", token);
        JsonArray data = new JsonArray();
        try {
            // 发送请求并获取响应
            HttpResponse response = httpClient.execute(httpGet);
            String res = EntityUtils.toString(response.getEntity(), "utf-8");
            if (res.contains("<h")) {
                System.out.println("服务异常；" + res);
                return new JsonArray();
            }
            JSONObject responses = JSONObject.parseObject(res);
            if (responses.get("statuses") != null) {
                JsonArray list = new JsonParser().parse(responses.get("statuses").toString()).getAsJsonArray();
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return data;
    }

    /**
     *很显然这是一个post请求用来获取返回json
     **/
    public static JSONObject post(JSONObject request, String url, String token, String cookie) throws IOException,
            Exception {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Cookie", cookie);
        httpPost.addHeader("X-Xsrf-Token", token);
        httpPost.setEntity(new StringEntity(request.toJSONString(), "utf-8"));
        HttpResponse response = httpClient.execute(httpPost);
        String res = EntityUtils.toString(response.getEntity(), "utf-8");
        if (res.contains("<h")) {
            System.out.println("服务异常；" + res);
            return new JSONObject();
        }
        JSONObject resJson = JSONObject.parseObject(res);
        JSONObject resState = (JSONObject) JSONObject.toJSON(response.getStatusLine());
        if (resState != null && "200".equals(resState.get("statusCode").toString())
                && "1".equals(resJson.get("ok").toString())) {
            JsonArray list = new JsonParser().parse(resJson.get("statuses").toString()).getAsJsonArray();
            return JSONObject.parseObject(String.valueOf(list.get(0)));
        } else {
            return new JSONObject();
        }
    }


    /**
     *这是一个处理函数，自动拼接输出内容
     **/
    public static void system(String[] text) {
        StringBuffer buffer = new StringBuffer();
        List<String> list = Arrays.asList(text);
        for(int i=0;i<list.size();i++){
            if(i%2==0){
                buffer.append(StringUtils.isNotBlank(list.get(i))?list.get(i):"");
            }else{
                buffer.append(StringUtils.isNotBlank(list.get(i))?String.format("\033[%d;%dm%s\033[0m", 1, 31, list.get(i)):"");
            }
        }
        System.out.println(buffer);
    }


}


