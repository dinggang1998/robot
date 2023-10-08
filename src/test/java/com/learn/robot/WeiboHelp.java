package com.learn.robot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 这是一个自动微博设置工具
 * 5463014783
 * 6491843419
 * 5612253786
 * 5840431155
 * 5367243364
 * 5240314699
 **/
public class WeiboHelp {

    int number=0;

    @Test
    public void test() throws Exception {
//        changeType(
//                "5612253786","2",false,"",
//                "86ZrOA9umOtxvqqGkHdzDapu",
//                "XSRF-TOKEN=86ZrOA9umOtxvqqGkHdzDapu; _s_tentry=weibo.com; Apache=811696460495.4924.1693537780253; " +
//                        "SINAGLOBAL=811696460495.4924.1693537780253; ULV=1693537780331:1:1:1:811696460495.4924" +
//                        ".1693537780253:; SSOLoginState=1693540931; " +
//                        "SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5GPa58Qr8QzZ56s0U7h9Cd5JpX5KMhUgL.FoMf1h20SK" +
//                        ".p1hz2dJLoIEXLxKqL1-eL1hnLxKBLB.2LB--LxKBLBonL12BLxK-LBKBLB-2LxK-L1hML1h.t; ALF=1696744447; " +
//                        "SCF=AkZoQxSmjIW6jJNMSCgd4QqudWd7siSdwucbr3BJNNP-dGScKzn4BB6hywW9eVKB05_-JGnZOO7ofKDT6oX1110" +
//                        ".; SUB=_2A25J_sdQDeThGeFL41MS9SfNwz6IHXVqjb-YrDV8PUNbmtAGLWnMkW9NfbaweDS" +
//                        "-X3xO1MYgPRS427X9rqLFKSct; " +
//                        "WBPSESS" +
//                        "=4rTOAjYI2YBXhdIZPlI4AGF3DMdE63JkYcqypUjcCaYNf1zcKqTKp52NZSy5RuliucwXWUHmt20c3fcZCxt0jOz51o9OR64QY8cxBbwy0NEb7092onfnj-qFSaGkXrE5lOfTyFweISgjlt1AXjWHnA=="
//        );
//        getMessage(
//                "100057581359182",
//                "86ZrOA9umOtxvqqGkHdzDapu",
//                "XSRF-TOKEN=86ZrOA9umOtxvqqGkHdzDapu; _s_tentry=weibo.com; Apache=811696460495.4924.1693537780253; " +
//                        "SINAGLOBAL=811696460495.4924.1693537780253; ULV=1693537780331:1:1:1:811696460495.4924" +
//                        ".1693537780253:; SSOLoginState=1693540931; " +
//                        "SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5GPa58Qr8QzZ56s0U7h9Cd5JpX5KMhUgL.FoMf1h20SK" +
//                        ".p1hz2dJLoIEXLxKqL1-eL1hnLxKBLB.2LB--LxKBLBonL12BLxK-LBKBLB-2LxK-L1hML1h.t; ALF=1696744447; " +
//                        "SCF=AkZoQxSmjIW6jJNMSCgd4QqudWd7siSdwucbr3BJNNP-dGScKzn4BB6hywW9eVKB05_-JGnZOO7ofKDT6oX1110" +
//                        ".; SUB=_2A25J_sdQDeThGeFL41MS9SfNwz6IHXVqjb-YrDV8PUNbmtAGLWnMkW9NfbaweDS" +
//                        "-X3xO1MYgPRS427X9rqLFKSct; " +
//                        "WBPSESS" +
//                        "=4rTOAjYI2YBXhdIZPlI4AGF3DMdE63JkYcqypUjcCaYNf1zcKqTKp52NZSy5RuliucwXWUHmt20c3fcZCxt0jOz51o9OR64QY8cxBbwy0NEb7092onfnj-qFSaGkXrE5lOfTyFweISgjlt1AXjWHnA=="
//        );
//        System.out.println("==========================这是一条分割线===============================================");
        getAllMessage(
                "5463014783",
                "86ZrOA9umOtxvqqGkHdzDapu",
                "XSRF-TOKEN=86ZrOA9umOtxvqqGkHdzDapu; _s_tentry=weibo.com; Apache=811696460495.4924.1693537780253; SINAGLOBAL=811696460495.4924.1693537780253; ULV=1693537780331:1:1:1:811696460495.4924.1693537780253:; SSOLoginState=1694157356; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5ZmyoRKcYPlj5kHPT1ziWS5JpX5KMhUgL.Fo-ceKzESKeN1hq2dJLoIEqLxKnL1hMLB-2LxKqL1hnL1K2LxK-LBK5L1K-LxKnL1K5LB.x.9Btt; ALF=1697682876; SCF=AkZoQxSmjIW6jJNMSCgd4QqudWd7siSdwucbr3BJNNP-g1Qu2PFP-dfMWD-y_3aINg5MSevx2o2QHjBaCpVjoew.; SUB=_2A25IDXjuDeRhGeNI6lAT9S3LwzqIHXVre-0mrDV8PUNbmtB-LU2jkW9NSLbRCwAAT3ICDU4riIyHjiEBPlq-1cjY; WBPSESS=mLhuWlHwATZhowpaqRnfxaeoO5e6sJz4G6j3ZCIkflLeHCpTEVeYaz0PG_LNlsg0jamwYKFCYXSLkSiGSE5zJbW-Fsl749szcdNxoY0vpiknD0hQTVrihzlL3nS4cksyXn-5Ogor-frVxMmGh06lGw=="
        );

    }


    /**
     * 修改自己微博的权限
     * type (0公开，1隐藏，2好友圈可见)
     * appoint (true，指定的改某种类型，false，所有的都改)
     * originalType 需要处理的原来的微博状态，只有appoint为true才需要该参数
     **/
    public void changeType(String uid,String type, boolean appoint, String originalType,String token, String cookie) throws Exception {
        JSONObject userInfo = get("https://weibo.com/ajax/profile/info?uid=" + uid, token, cookie);
        if (userInfo.get("user") != null) {
            String totalNum = userInfo.getJSONObject("user").getString("statuses_count");
            String userName = userInfo.getJSONObject("user").getString("screen_name");
            int page = (Integer.valueOf(totalNum) / 27) + 1;
            system(new String[]{"当前用户为：", userName});
            system(new String[]{"微博总数量为：", totalNum});
            system(new String[]{"微博页数：", String.valueOf(page)});
            Map weiboMap = new HashMap();
            List<String> midList = new ArrayList<>();
            for (int i = 1; i <= page; i++) {
                JSONObject response = get("https://weibo.com/ajax/statuses/mymblog?uid=" + uid + "&page=" + i + "&feature=0", token, cookie);
                JSONArray weiboMidList = response.getJSONArray("list");
                for (int j = 0; j < weiboMidList.size(); j++) {
                    String mid = weiboMidList.getJSONObject(j).getString("mid");
                    String visibleType = weiboMidList.getJSONObject(j).getJSONObject("visible").getString("type");
                    if (!appoint) {
                        //只处理需要处理的微博
                        if (!type.equals(visibleType)) {
                            midList.add(mid);
                            weiboMap.put(mid, weiboMidList.getJSONObject(j).getString("text"));
                        }
                    } else {
                        if (originalType.equals(visibleType)) {
                            midList.add(mid);
                            weiboMap.put(mid, weiboMidList.getJSONObject(j).getString("text"));
                        }
                    }
                }
            }
            system(new String[]{"需要处理", String.valueOf(midList.size()), "条微博","32"});
            if (CollectionUtils.isNotEmpty(midList)) {
                for (String mid : midList) {
                    JSONObject request = new JSONObject();
                    request.put("ids", mid);
                    request.put("visible", type);
                    JSONObject data = post(request, "https://weibo.com/ajax/statuses/modifyVisible", token, cookie);
                    if (data != null && data.get("mid") != null) {
                        system(new String[]{"微博：", data.getString("text"), "已设置为", data.getJSONObject("title").getString("text"), "33"});
                    } else {
                        system(new String[]{"微博：", weiboMap.get(mid).toString(), "", "设置失败", "mid：", mid, "34"});
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
     * 获取某固定用户的微博内容
     **/
    public void getAllMessage(String uid, String token, String cookie) throws Exception {

        JSONObject userInfo = get("https://weibo.com/ajax/profile/info?uid=" + uid, token, cookie);
        if (userInfo.get("user") != null) {
            String totalNum = userInfo.getJSONObject("user").getString("statuses_count");
            String userName = userInfo.getJSONObject("user").getString("screen_name");
            int page = (Integer.valueOf(totalNum) / 27) + 1;
            system(new String[]{"当前用户为：", userName});
            system(new String[]{"微博总数量为：", totalNum});
            system(new String[]{"微博页数：", String.valueOf(page),"31"});
            for (int i = 1; i <= page; i++) {
                JSONObject response = get("https://weibo.com/ajax/statuses/mymblog?uid=" + uid + "&page=" + i + "&feature=0", token, cookie);
                JSONArray weiboMidlist = response.getJSONArray("list");
                for (int j = 0; j < weiboMidlist.size(); j++) {
                    String mid = weiboMidlist.getJSONObject(j).getString("mid");
                    String textRaw = weiboMidlist.getJSONObject(j).getString("text_raw");
                    String createdAt = dateConversion(weiboMidlist.getJSONObject(j).getString("created_at"));
                    int commentsCount = weiboMidlist.getJSONObject(j).getInteger("comments_count");
                    int attitudesCount = weiboMidlist.getJSONObject(j).getInteger("attitudes_count");
                    int attitudesStatus = weiboMidlist.getJSONObject(j).getInteger("attitudes_status");
                    String source = weiboMidlist.getJSONObject(j).getOrDefault("source", "未知终端").toString();
                    source = source.contains("<") ? source.substring(source.indexOf(">") + 1, source.indexOf("</")) : source;
                    String regionName = weiboMidlist.getJSONObject(j).getOrDefault("region_name", "发布于未知").toString();
                    if (null == weiboMidlist.getJSONObject(j).getJSONObject("title")) {
                        system(new String[]{"===================================第" + (((i - 1) * 27) + j + 1) + "条微博========================================", "35"});
                        system(new String[]{"发布时间：", createdAt});
                        system(new String[]{"发布地：", regionName.replaceAll("发布于", ""), ",来自于：", source, "32"});
                        system(new String[]{"微博内容：", textRaw.replaceAll("\\n", ""), "33"});
                        system(new String[]{"有", String.valueOf(commentsCount), "人评论，有", String.valueOf(attitudesCount), "人点赞,您", attitudesStatus == 0 ? "还没有" : "已经", "点赞", "34"});
                        if (commentsCount != 0) {
                            JSONArray commentsList = getList("https://weibo.com/ajax/statuses/buildComments?is_reload=1&id=" + mid
                                    + "&is_show_bulletin=3&is_mix=0&count=20&type=feed&uid=" + uid + "&fetch_level=0&locale=zh-CN", token, cookie);
                            if (!commentsList.isEmpty()) {
                                for (int s = 0; s < commentsList.size(); s++) {
                                    JSONObject comment = commentsList.getJSONObject(s);
                                    if (comment.getJSONObject("user") != null) {
                                        String screenNameDetail = comment.getJSONObject("user").getString("screen_name");
                                        String sourceDetail = comment.get("source") != null ? comment.getString("source") : "未知";
                                        String textRawDetail = comment.getString("text_raw");
                                        int totalNumber = comment.getInteger("total_number");
                                        String rootid = comment.getString("rootid");
                                        system(new String[]{"", sourceDetail, "的", screenNameDetail, "评论到", textRawDetail, "35"});
                                        if (totalNumber != 0) {
                                            JSONArray commontDetails = comment.getJSONArray("comments");
                                            if (!commontDetails.isEmpty() && totalNumber < 20) {
                                                commontDetails.stream().forEach(obj -> {
                                                    JSONObject commentsDetail = (JSONObject) JSON.toJSON(obj);
                                                    if (commentsDetail.get("user") != null) {
                                                        String screenNameDetailDel = commentsDetail.getJSONObject("user").getString("screen_name");
                                                        String sourceDetailDel = commentsDetail.getOrDefault("source", "未知").toString();
                                                        String textRawDetailDel = commentsDetail.getString("text_raw");
                                                        system(new String[]{"", sourceDetailDel, "的", screenNameDetailDel, "回复", sourceDetail, "的", screenNameDetail, "评论到", textRawDetailDel, "36"});
                                                    }
                                                });
                                            }
                                            if((!commontDetails.isEmpty() && totalNumber >= 20)||commontDetails.isEmpty()){
                                                JSONArray commentsDetailList = getList("https://weibo.com/ajax/statuses/buildComments?is_reload=1&id=" + rootid +
                                                        "&is_show_bulletin=2&is_mix=1&fetch_level=1&max_id=0&count=20&uid=" + uid + "&locale=zh-CN", token, cookie);
                                                if (!commentsDetailList.isEmpty()) {
                                                    commentsDetailList.stream().forEach(obj -> {
                                                        JSONObject commentsDetails = (JSONObject) JSON.toJSON(obj);
                                                        if (JSONObject.parseObject(commentsDetails.toString()).get("user") != null) {
                                                            String screenNameDetailDel = commentsDetails.getJSONObject("user").getString("screen_name");
                                                            String sourceDetailDel = commentsDetails.getOrDefault("source", "未知").toString();
                                                            String textRawDetailDel = commentsDetails.getString("text_raw");
                                                            system(new String[]{"", sourceDetailDel, "的", screenNameDetailDel, "回复", sourceDetail, "的", screenNameDetail, "评论到", textRawDetailDel, "36"});
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("获取结束啦！");
        } else {
            System.err.println("执行异常！");
        }
    }

    /**
     * 获取悄悄关注的列表页内容
     **/
    public void getMessage(String listId, String token, String cookie) throws IOException, Exception {
        JSONArray messageInfos = getList("https://weibo.com/ajax/feed/groupstimeline?list_id=" + listId + "&refresh=4&fast_refresh=1&count=25", token, cookie);
        if (messageInfos != null && messageInfos.size() > 0) {
            messageInfos.stream().forEach(object -> {
                JSONObject messageInfo = (JSONObject) JSON.toJSON(object);
                String screenName = messageInfo.getJSONObject("user").getString("screen_name");
                String createdAt = messageInfo.getString("created_at");
                String textRaw = messageInfo.getString("text_raw");
                System.out.println("===========================================================================");
                system(new String[]{"用户名", screenName, "32"});
                system(new String[]{"发布时间", createdAt, "33"});
                system(new String[]{"微博内容", textRaw, "34"});
            });
        } else {
            System.err.println("执行异常！");
        }
    }

    /**
     * 很显然这是一个get请求用来获取返回json
     **/
    public JSONObject get(String url, String token, String cookie) throws IOException, Exception {
        number=number+1;
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-Type", "application/json");
        httpGet.addHeader("Cookie", cookie);
        httpGet.addHeader("X-Xsrf-Token", token);
        JSONObject data = new JSONObject();
        try {
            HttpResponse response = httpClient.execute(httpGet);
            String res = EntityUtils.toString(response.getEntity(), "utf-8");
            if (res.contains("<h")) {
                System.out.println("服务异常；" + res);
                return new JSONObject();
            }
            JSONObject responses = JSONObject.parseObject(res);
            if (responses.get("data") != null) {
                data = responses.getJSONObject("data");
            }
            if (responses.get("statuses") != null) {
                data = responses.getJSONArray("statuses").getJSONObject(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return data;
    }

    /**
     * 很显然这是一个get请求用来获取返回json数组
     **/
    public JSONArray getList(String url, String token, String cookie) throws IOException, Exception {
        number=number+1;
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-Type", "application/json");
        httpGet.addHeader("Cookie", cookie);
        httpGet.addHeader("X-Xsrf-Token", token);
        JSONArray data = new JSONArray();
        try {
            HttpResponse response = httpClient.execute(httpGet);
            String res = EntityUtils.toString(response.getEntity(), "utf-8");
            if (res.contains("<h")) {
                System.out.println("服务异常；" + res);
                return new JSONArray();
            }
            JSONObject responses = JSONObject.parseObject(res);
            if (responses.get("statuses") != null) {
                JSONArray list = responses.getJSONArray("statuses");
                return list;
            }
            if (responses.get("data") != null) {
                JSONArray list = responses.getJSONArray("data");
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
     * 很显然这是一个post请求用来获取返回json
     **/
    public JSONObject post(JSONObject request, String url, String token, String cookie) throws IOException, Exception {
        number=number+1;
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
        if (resState != null && "200".equals(resState.getString("statusCode"))
                && "1".equals(resJson.get("ok").toString())) {
            JSONArray list = resJson.getJSONArray("statuses");
            return list.getJSONObject(0);
        } else {
            return new JSONObject();
        }
    }


    /**
     * 这是一个处理函数，自动拼接输出内容
     **/
    private void system(String[] text) {
        StringBuffer buffer = new StringBuffer();
        List<String> list = new ArrayList<>(Arrays.asList(text));
        String type = list.get(list.size() - 1);
        if ("3".equals(type.substring(0, 1))) {
            list.remove(list.size() - 1);
        } else {
            type = "31";
        }
        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                buffer.append(StringUtils.isNotBlank(list.get(i)) ? list.get(i) : "");
            } else {
                buffer.append(StringUtils.isNotBlank(list.get(i)) ? String.format("\033[%d;%dm%s\033[0m", 1, Integer.valueOf(type), list.get(i)) : "");
            }
        }
        System.out.println(buffer);
    }

    private String getString(Map map, String key) {
        String result = "";
        if (map != null && map.containsKey(key)) {
            result = String.valueOf(null == map.get(key) ? "" : map.get(key));
        }
        return result;
    }

    /**
     * 这是一个英文时间转换格式工具
     */
    private String dateConversion(String EnglishDate) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = null;
        try {
            parse = sdf1.parse(EnglishDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf2.format(parse);
    }


}


