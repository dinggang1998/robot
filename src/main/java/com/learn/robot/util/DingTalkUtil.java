package com.learn.robot.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;



@Component
public class DingTalkUtil {


    /**
     * @param message 所要发送的消息
     * @return 发送状态回执
     */
    public static String postWithJson(String title, String message, boolean isAtAll) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msgtype", "text");
            jsonObject.put("msgtype", "markdown");
            JSONObject markdown = new JSONObject();
            //此处message是你想要发送到钉钉的信息
            markdown.put("title", title);
            markdown.put("text", message);
            jsonObject.put("markdown", markdown);
            JSONObject at = new JSONObject();
            at.put("isAtAll", isAtAll);
            jsonObject.put("at", at);
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(getSign());
            httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
            httpPost.setEntity(new StringEntity(jsonObject.toJSONString(), "utf-8"));
            HttpResponse execute = httpClient.execute(httpPost);

            if (execute.getStatusLine().toString().contains("200")) {
                return execute.toString();
            }
        } catch (IOException e) {
            System.out.println("消息发送失败！");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("签名获取失败");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取签名
     *
     * @return 返回签名
     */
    private static String getSign() throws Exception {
        String baseUrl = "https://oapi.dingtalk.com/robot/send?access_token=";
        String token = "e77d91114d757fa9c5d133ffe20d7ff0a87f8e201079f88971094ec6910fe4b2";
        String secret = "SEC1bec08a2cfdba080ed498a508f2da5790675251a6171c3d2390c22365d18ff8e";
        long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
        return baseUrl + token + "&timestamp=" + timestamp + "&sign=" +
                URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
    }


    public static String getToken() {
//        try {
//            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
//            OapiGettokenRequest req = new OapiGettokenRequest();
//            req.setAppkey("dingp1yqs8n7zahffoyu");
//            req.setAppsecret("Y8BJOu-tQlFVW2mssL3MpfmOqfYRfZebRee27O5VAY6jD6-n56XBuZ_M7gcjGBTu");
//            req.setHttpMethod("GET");
//            OapiGettokenResponse rsp = client.execute(req);
//            System.out.println(rsp.getBody());
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
        return "";
    }

}
