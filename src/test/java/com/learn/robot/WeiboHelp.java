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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这是一个自动微博设置工具
 * 5463014783  猪
 * 6491843419  猪
 * 5612253786  我
 * 5840431155  乐
 * 5367243364  孙
 * 5240314699  莹
 * 6622274706 juan
 **/

/**
 * type： 0 公开 1 仅自己可见 2 好友可见
 */
public class WeiboHelp {

    int number = 0;

    @Test
    public void test() throws Exception {
//        changeType(
//                "5612253786","1",true,"0",
//                "OpNggY7dRAdPSyn3bMa2JdbJ",
//                "XSRF-TOKEN=OpNggY7dRAdPSyn3bMa2JdbJ; _s_tentry=me.weibo.com; Apache=1415743048300.906.1706776743451;" +
//                        " SINAGLOBAL=1415743048300.906.1706776743451; ULV=1706776743463:1:1:1:1415743048300.906" +
//                        ".1706776743451:; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5ZmyoRKcYPlj5kHPT1ziWS5JpX5KMhUgL" +
//                        ".Fo-ceKzESKeN1hq2dJLoIEqLxKnL1hMLB-2LxKqL1hnL1K2LxK-LBK5L1K-LxKnL1K5LB.x.9Btt; " +
//                        "ALF=1718354282; " +
//                        "SUB=_2A25LQAQ4DeRhGeNI6lAT9S3LwzqIHXVoPBnwrDV8PUJbkNAGLXHzkW1NSLbRC0zJoMFdGWOBvv5-5-g1l7a9" +
//                        "-EeP; WBPSESS=mLhuWlHwATZhowpaqRnfxaeoO5e6sJz4G6j3ZCIkflLeHCpTEVeYaz0PG_LNlsg0mYb5IsZ0" +
//                        "-5ARp3de2xrJ5j9ZWGZueR2-qtVFhpmutmtZILeDamiARdch6bKtr76Ro5uxd7R-u9IeCVvWoQQoKQ==");
//        getMessage(
//                "100057581359182",
//                "86ZrOA9umOtxvqqGkHdzDapu",
//                "XSRF-TOKEN=86ZrOA9umOtxvqqGkHdzDapu; _s_tentry=weibo.com; Apache=811696460495.4924.1693537780253;
//                SINAGLOBAL=811696460495.4924.1693537780253; ULV=1693537780331:1:1:1:811696460495.4924
//                .1693537780253:; SSOLoginState=1694157356; login_sid_t=d0de98884398d972aabddcd34395087f;
//                cross_origin_proto=SSL; UOR=,,login.sina.com.cn;
//                SCF=AkZoQxSmjIW6jJNMSCgd4QqudWd7siSdwucbr3BJNNP-sO_tdfT-odWZXhDr41OpDL0AGjwSJrXZrEJnbgdRztk.;
//                SUB=_2A25Ij6-oDeRhGeNI6lAT9S3LwzqIHXVr5K1grDV8PUNbmtAGLRWlkW9NSLbRC06GfXydofxiZZKXHKKrXgs-4xZT;
//                SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5ZmyoRKcYPlj5kHPT1ziWS5JpX5KzhUgL
//                .Fo-ceKzESKeN1hq2dJLoIEqLxKnL1hMLB-2LxKqL1hnL1K2LxK-LBK5L1K-LxKnL1K5LB.x.9Btt; ALF=1706257658;
//                WBPSESS=mLhuWlHwATZhowpaqRnfxaeoO5e6sJz4G6j3ZCIkflLeHCpTEVeYaz0PG_LNlsg0jamwYKFCYXSLkSiGSE5zJYbYZxM1RszREptZHoRezd9ehOKJSOw-QewV2wMPNlkgkccjJi7sPIUsV4PS-N0Z1Q=="
//        );
//        System.out.println("==========================这是一条分割线===============================================");
        getAllMessage(
                "5463014783",
                "OpNggY7dRAdPSyn3bMa2JdbJ",
                "XSRF-TOKEN=OpNggY7dRAdPSyn3bMa2JdbJ; _s_tentry=me.weibo.com; Apache=1415743048300.906.1706776743451;" +
                        " SINAGLOBAL=1415743048300.906.1706776743451; ULV=1706776743463:1:1:1:1415743048300.906" +
                        ".1706776743451:; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5ZmyoRKcYPlj5kHPT1ziWS5JpX5KMhUgL" +
                        ".Fo-ceKzESKeN1hq2dJLoIEqLxKnL1hMLB-2LxKqL1hnL1K2LxK-LBK5L1K-LxKnL1K5LB.x.9Btt; " +
                        "ALF=1718354282; " +
                        "SUB=_2A25LQAQ4DeRhGeNI6lAT9S3LwzqIHXVoPBnwrDV8PUJbkNAGLXHzkW1NSLbRC0zJoMFdGWOBvv5-5-g1l7a9" +
                        "-EeP; WBPSESS=mLhuWlHwATZhowpaqRnfxaeoO5e6sJz4G6j3ZCIkflLeHCpTEVeYaz0PG_LNlsg0mYb5IsZ0" +
                        "-5ARp3de2xrJ5j9ZWGZueR2-qtVFhpmutmtZILeDamiARdch6bKtr76Ro5uxd7R-u9IeCVvWoQQoKQ==");
//        JSONObject picture = get("https://weibo.com/ajax/friendships/friends?relate=fans&page=1&uid=6491843419&type=all&newFollowerCount=0",
//                "86ZrOA9umOtxvqqGkHdzDapu",
//                "XSRF-TOKEN=86ZrOA9umOtxvqqGkHdzDapu; _s_tentry=weibo.com; Apache=811696460495.4924.1693537780253;
//                SINAGLOBAL=811696460495.4924.1693537780253; ULV=1693537780331:1:1:1:811696460495.4924
//                .1693537780253:; SSOLoginState=1694157356; login_sid_t=d0de98884398d972aabddcd34395087f;
//                cross_origin_proto=SSL; UOR=,,login.sina.com.cn;
//                SCF=AkZoQxSmjIW6jJNMSCgd4QqudWd7siSdwucbr3BJNNP-sO_tdfT-odWZXhDr41OpDL0AGjwSJrXZrEJnbgdRztk.;
//                SUB=_2A25Ij6-oDeRhGeNI6lAT9S3LwzqIHXVr5K1grDV8PUNbmtAGLRWlkW9NSLbRC06GfXydofxiZZKXHKKrXgs-4xZT;
//                SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5ZmyoRKcYPlj5kHPT1ziWS5JpX5KzhUgL
//                .Fo-ceKzESKeN1hq2dJLoIEqLxKnL1hMLB-2LxKqL1hnL1K2LxK-LBK5L1K-LxKnL1K5LB.x.9Btt; ALF=1706257658;
//                WBPSESS=mLhuWlHwATZhowpaqRnfxaeoO5e6sJz4G6j3ZCIkflLeHCpTEVeYaz0PG_LNlsg0jamwYKFCYXSLkSiGSE5zJYbYZxM1RszREptZHoRezd9ehOKJSOw-QewV2wMPNlkgkccjJi7sPIUsV4PS-N0Z1Q=="
//        );
//        JSONObject picture1 = get("https://weibo.com/ajax/friendships/friends?page=1&uid=6491843419",
//                "86ZrOA9umOtxvqqGkHdzDapu",
//                "XSRF-TOKEN=86ZrOA9umOtxvqqGkHdzDapu; _s_tentry=weibo.com; Apache=811696460495.4924.1693537780253;
//                SINAGLOBAL=811696460495.4924.1693537780253; ULV=1693537780331:1:1:1:811696460495.4924
//                .1693537780253:; SSOLoginState=1694157356; login_sid_t=d0de98884398d972aabddcd34395087f;
//                cross_origin_proto=SSL; UOR=,,login.sina.com.cn;
//                SCF=AkZoQxSmjIW6jJNMSCgd4QqudWd7siSdwucbr3BJNNP-sO_tdfT-odWZXhDr41OpDL0AGjwSJrXZrEJnbgdRztk.;
//                SUB=_2A25Ij6-oDeRhGeNI6lAT9S3LwzqIHXVr5K1grDV8PUNbmtAGLRWlkW9NSLbRC06GfXydofxiZZKXHKKrXgs-4xZT;
//                SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9W5ZmyoRKcYPlj5kHPT1ziWS5JpX5KzhUgL
//                .Fo-ceKzESKeN1hq2dJLoIEqLxKnL1hMLB-2LxKqL1hnL1K2LxK-LBK5L1K-LxKnL1K5LB.x.9Btt; ALF=1706257658;
//                WBPSESS=mLhuWlHwATZhowpaqRnfxaeoO5e6sJz4G6j3ZCIkflLeHCpTEVeYaz0PG_LNlsg0jamwYKFCYXSLkSiGSE5zJYbYZxM1RszREptZHoRezd9ehOKJSOw-QewV2wMPNlkgkccjJi7sPIUsV4PS-N0Z1Q=="
//        );

    }


    /**
     * 修改自己微博的权限
     * type (0公开，1隐藏，2好友圈可见)
     * appoint (true，指定的改某种类型，false，所有的都改)
     * originalType 需要处理的原来的微博状态，只有appoint为true才需要该参数
     **/
    public void changeType(String uid, String type, boolean appoint, String originalType, String token,
                           String cookie) throws Exception {
        JSONObject userInfo = get("https://weibo.com/ajax/profile/info?uid=" + uid, token, cookie);
        if (userInfo.get("user") != null) {
            systemUserMessage(userInfo);
            Map weiboMap = new HashMap();
            String totalNum = userInfo.getJSONObject("user").getString("statuses_count");
            int page = (Integer.valueOf(totalNum) / 27) + 1;
            List<String> midList = processWeiboPosts(uid, type, appoint, token, cookie, page, weiboMap,originalType);
            system(new String[]{"需要修改", String.valueOf(midList.size()), "条微博", "32"});
            if (CollectionUtils.isNotEmpty(midList)) {
                for (String mid : midList) {
                    try {
                        modifyWeiboVisible(mid, weiboMap.get(mid).toString(), type, token, cookie);
                    } catch (Exception e) {
                        system(new String[]{"微博：", weiboMap.get(mid).toString(), "", "设置失败", "mid：", mid, "34"});
                    }
                }
            }
            System.out.println("已经设置完成咯！");
        } else {
            System.err.println("执行异常！");
        }
    }


    private List<String> processWeiboPosts(String uid, String type, boolean appoint, String token, String cookie,
                                           int page, Map<String, String> weiboMap,String originalType) throws Exception {
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
        return midList;
    }

    private void modifyWeiboVisible(String mid, String text, String type, String token, String cookie) throws Exception {
        JSONObject request = new JSONObject();
        request.put("ids", mid);
        request.put("visible", type);
        JSONObject data = post(request, "https://weibo.com/ajax/statuses/modifyVisible", token, cookie);
        if (data != null && data.get("mid") != null) {
            system(new String[]{"微博：", data.getString("text"), "已设置为", data.getJSONObject("title").getString("text"), "33"});
        } else {
            system(new String[]{"微博：", text, "", "设置失败", "mid：", mid, "34"});
        }
    }

    /**
     * 获取某固定用户的微博内容
     **/
    public void getAllMessage(String uid, String token, String cookie) throws Exception {
        JSONObject userInfo = get("https://weibo.com/ajax/profile/info?uid=" + uid, token, cookie);
        //获取用户的基本信息
        if (userInfo.get("user") != null) {
            systemUserMessage(userInfo);
            String totalNum = userInfo.getJSONObject("user").getString("statuses_count");
            int page = (Integer.valueOf(totalNum) / 27) + 1;
            for (int i = 1; i <= page; i++) {
                //获取每一页的27条微博具体内容
                JSONObject response = get("https://weibo.com/ajax/statuses/mymblog?uid=" + uid + "&page=" + i + "&feature=0", token, cookie);
                JSONArray weiboMidlist = response.getJSONArray("list");
                for (int j = 0; j < weiboMidlist.size(); j++) {
                    //打印每条微博的具体内容
                    String mid = weiboMidlist.getJSONObject(j).getString("mid");
                    boolean isLongText = weiboMidlist.getJSONObject(j).getBoolean("isLongText");
                    String textRaw = weiboMidlist.getJSONObject(j).getString("text_raw");
                    //判断是否长微博，长微博需要重新通过接口获取微博内容
                    if (isLongText) {
                        String mblogid = weiboMidlist.getJSONObject(j).getString("mblogid");
                        JSONObject textDetailJson = get("https://weibo.com/ajax/statuses/longtext?id=" + mblogid, token, cookie);
                        String longTextContent = textDetailJson.getOrDefault("longTextContent", "").toString();
                        textRaw = longTextContent;
                    }
                    //获取微博的一些基本信息
                    String createdAt = dateConversion(weiboMidlist.getJSONObject(j).getString("created_at"));
                    int commentsCount = weiboMidlist.getJSONObject(j).getInteger("comments_count");
                    int attitudesCount = weiboMidlist.getJSONObject(j).getInteger("attitudes_count");
                    int attitudesStatus = weiboMidlist.getJSONObject(j).getInteger("attitudes_status");
                    String source = weiboMidlist.getJSONObject(j).getOrDefault("source", "未知终端").toString();
                    source = source.contains("<") ? source.substring(source.indexOf(">") + 1, source.indexOf("</")) : source;
                    String regionName = weiboMidlist.getJSONObject(j).getOrDefault("region_name", "发布于未知").toString();
                    String thisWeiboId = weiboMidlist.getJSONObject(j).getJSONObject("user").getString("id");
                    //获取微博中图片路径，需要路由从微博的ip请求才可以访问
                    JSONArray picIds = weiboMidlist.getJSONObject(j).getJSONArray("pic_ids");
                    JSONObject picInfos = weiboMidlist.getJSONObject(j).getJSONObject("pic_infos");
                    StringBuffer picture = new StringBuffer();
                    if (CollectionUtils.isNotEmpty(picIds) && null != picInfos) {
                        for (int z = 0; z < picIds.size(); z++) {
                            JSONObject picInfo = picInfos.getJSONObject(picIds.get(z).toString());
                            String pictureUrl = picInfo.getJSONObject("original").getOrDefault("url", "").toString();
                            picture.append(pictureUrl);
                            if (z != picIds.size() - 1) picture.append("\n");
                        }
                    }
                    //获取转发微博的原内容
                    JSONObject retweetedStatus = weiboMidlist.getJSONObject(j).getJSONObject("retweeted_status");
                    system(new String[]{"===================================第" + (((i - 1) * 27) + j + 1) + "条微博========================================", "35"});
                    if (!thisWeiboId.equals(uid)) {
                        String thisTitle = weiboMidlist.getJSONObject(j).getJSONObject("title").getString("text");
                        system(new String[]{"这条是TA赞过的微博：", thisTitle});
                    }
                    system(new String[]{"发布时间：", createdAt});
                    system(new String[]{"发布地：", dealString(regionName), ",来自于：", source, "32"});
                    system(new String[]{"微博内容：\n", wrap(dealString(textRaw), 65), "33"});
                    if (retweetedStatus != null) {
                        String textRawDetail = retweetedStatus.getString("text_raw");
                        String originalUserName = retweetedStatus.getJSONObject("user").getOrDefault("screen_name", "").toString();
                        system(new String[]{"转发自", originalUserName, "的微博：", wrap(dealString(textRawDetail), 90), "35"});
                    }
                    if (StringUtils.isNotBlank(String.valueOf(picture)))
                        system(new String[]{"微博图片：", picture.toString(), "33"});
                    system(new String[]{"有", String.valueOf(commentsCount), "人评论，有", String.valueOf(attitudesCount), "人点赞,您", attitudesStatus == 0 ? "还没有" : "已经", "点赞", "34"});
                    if (commentsCount != 0) {
                        getAllComments(mid, uid, token, cookie);
                    }
                }
            }
            System.out.println("获取结束啦！");
        } else {
            System.err.println("执行异常！");
        }
    }

    public void getAllComments(String mid, String uid, String token, String cookie) throws Exception {
        JSONArray commentsList = getList("https://weibo.com/ajax/statuses/buildComments?is_reload=1&id=" + mid
                        + "&is_show_bulletin=3&is_mix=0&count=20&type=feed&uid=" + uid + "&fetch_level=0&locale=zh-CN"
                , token
                , cookie);
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
                                    String screenNameDetailDel = commentsDetail.getJSONObject("user").getString(
                                            "screen_name");
                                    String sourceDetailDel = commentsDetail.getOrDefault("source", "未知").toString();
                                    String textRawDetailDel = commentsDetail.getString("text_raw");
                                    system(new String[]{"", sourceDetailDel, "的", screenNameDetailDel, "回复",
                                            sourceDetail, "的", screenNameDetail, "评论到", textRawDetailDel, "36"});
                                }
                            });
                        }
                        if ((!commontDetails.isEmpty() && totalNumber >= 20) || commontDetails.isEmpty()) {
                            JSONArray commentsDetailList = getList("https://weibo.com/ajax/statuses/buildComments" +
                                    "?is_reload=1&id=" + rootid +
                                    "&is_show_bulletin=2&is_mix=1&fetch_level=1&max_id=0&count=20&uid=" + uid +
                                    "&locale=zh-CN", token, cookie);
                            if (!commentsDetailList.isEmpty()) {
                                commentsDetailList.stream().forEach(obj -> {
                                    JSONObject commentsDetails = (JSONObject) JSON.toJSON(obj);
                                    if (JSONObject.parseObject(commentsDetails.toString()).get("user") != null) {
                                        String screenNameDetailDel =
                                                commentsDetails.getJSONObject("user").getString("screen_name");
                                        String sourceDetailDel =
                                                commentsDetails.getOrDefault("source", "未知").toString();
                                        String textRawDetailDel = commentsDetails.getString("text_raw");
                                        system(new String[]{"", sourceDetailDel, "的", screenNameDetailDel, "回复",
                                                sourceDetail, "的", screenNameDetail, "评论到", textRawDetailDel, "36"});
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }


    }

    /**
     * 获取悄悄关注的列表页内容
     **/
    public void getMessage(String listId, String token, String cookie) throws IOException, Exception {
        JSONArray messageInfos = getList("https://weibo.com/ajax/feed/groupstimeline?list_id=" + listId + "&refresh=4" +
                "&fast_refresh=1&count=25", token, cookie);
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


    public void systemUserMessage(JSONObject userInfo) throws IOException, Exception {
        // 输出用户的基本信息
        String totalNum = userInfo.getJSONObject("user").getString("statuses_count");
        String userName = userInfo.getJSONObject("user").getString("screen_name");
        String description = userInfo.getJSONObject("user").getOrDefault("description", "无").toString();
        String location = userInfo.getJSONObject("user").getString("location");
        String followersCount = userInfo.getJSONObject("user").getString("followers_count");
        String friendsCount = userInfo.getJSONObject("user").getString("friends_count");
        String following = userInfo.getJSONObject("user").getString("following");
        String profileUrl = userInfo.getJSONObject("user").getString("profile_url");
        system(new String[]{"当前用户：", userName});
        system(new String[]{"简   介：", dealString(description)});
        system(new String[]{"当前I P：", location});
        system(new String[]{"微博总数：", totalNum});
        system(new String[]{"有", followersCount, "个关注，有", friendsCount, "个好友,您", "false".equals(following) ?
                "还没有" : "已经", "关注", "31"});
        system(new String[]{"微博链接：", "https://weibo.com/" + profileUrl});
//        system(new String[]{"微博页数：", String.valueOf(page), "31"});
    }

    /**
     * 很显然这是一个get请求用来获取返回json
     **/
    public JSONObject get(String url, String token, String cookie) throws IOException, Exception {
        number = number + 1;
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-Type", "application/json");
        httpGet.addHeader("Cookie", cookie);
        httpGet.addHeader("X-Xsrf-Token", token);
        httpGet.addHeader("Referer", "https://weibo.com/");
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
        number = number + 1;
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
        number = number + 1;
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
        if (StringUtils.isNotBlank(type) && "3".equals(type.substring(0, 1)) && type.length() == 2) {
            list.remove(list.size() - 1);
        } else {
            type = "31";
        }
        for (int i = 0; i < list.size(); i++) {
            if (i % 2 == 0) {
                buffer.append(StringUtils.isNotBlank(list.get(i)) ? list.get(i) : "");
            } else {
                buffer.append(StringUtils.isNotBlank(list.get(i)) ? String.format("\033[%d;%dm%s\033[0m", 1,
                        Integer.valueOf(type), list.get(i)) : "");
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

    private String wrap(String text, int number) {
        String wrap = "\n";
        int length = text.length();
        int total = length % number != 0 ? (length / number) + 1 : length / number;
        StringBuffer textNew = new StringBuffer();
        for (int i = 0; i < total; i++) {
            if (i == total - 1) {
                textNew.append(text.substring(i * number, text.length()));
            } else {
                textNew.append(text.substring(i * number, (i + 1) * number)).append(wrap);
            }
        }
        return textNew.toString();
    }

    private String dealString(String text) {
        return StringUtils.isNotBlank(text) ? text.replaceAll("\u200B", "")
                .replaceAll("\\n", "。")
                .replaceAll("发布于", "")
                : "无";
    }

    private String getJsonString(JSONObject json,String key1,String key2) {
        String text="";
        if(StringUtils.isNotBlank(key1)){
            text=json.getString(key1);
            if (StringUtils.isNotBlank(key2)){
                text=json.getJSONObject(key1).getString(key1);
            }
        }
        return text;
    }

}


