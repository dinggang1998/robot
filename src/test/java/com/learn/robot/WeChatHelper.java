//package com.learn.robot;
//
//import cn.hutool.http.HttpUtil;
//import cn.hyperchain.wechat.dto.MessageInfoDto;
//import cn.hyperchain.wechat.dto.WeChatLoginResponseDto;
//import cn.hyperchain.wechat.dto.WeChatPhoneResponseDto;
//import cn.hyperchain.wechat.dto.WechatInfo;
//import com.alibaba.fastjson.JSONObject;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
///**
// * 微信小程序开发相关接口工具.
// * 草，全他妈坑
// */
//@Slf4j
//public class WeChatHelper {
//    private final static String MINI_APP_ID = "xx";                         // 小程序ID
//    private final static String MINI_APP_SECRET = "xx";       // 小程序密钥
//    private final static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";       // 获取小程序/公众号token
//    private final static String PHONEURL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";
//    private final static String LOGINURL = "https://api.weixin.qq.com/sns/jscode2session";   // 微信小程序登录
//    private final static String OFFICIAL_ACCOUNT_APPID = "xx";                                  // 微信公众号appid
//    private final static String OFFICIAL_ACCOUNT_APP_SECRET = "xx";               // 微信公众号密钥
//    private final static String OFFICIAL_ACCOUNT_TEMPLATE_ID = "xx";   // 微信公众号历史模板消息id
//    private final static String OFFICIAL_ACCOUNT_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";  // 类目模版消息URL
//    private final static String OFFICIAL_ACCOUNT_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token="; // 微信公众号统一订阅消息URL，历史模版
//    private final static String OFFICIAL_ACCOUNT_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/get";      // 微信公众号获取所有用户列表
//    private final static String OFFICIAL_ACCOUNT_OPENID_URL = "https://api.weixin.qq.com/cgi-bin/user/info";         // 微信公众号获取用户个人信息（UnionID机制）
//    private final static String MINI_TEMPLATEID = "BWXslbbkk_Eo4Sz12VnZotYALExe43GDTgRG7HXBXRM";                             // 小程序一次性订阅消息模板ID【只能发一次】
//    private final static String MINI_MESSAGEURL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=";   // 小程序一次性订阅消息URL【只能发一次】
//
//    /**
//     * 1、根据微信code获取微信登录信息
//     *
//     * @param wxCode wx.login({
//     *               success: (res) => {
//     *               console.log("loginCode: ", res.code);
//     *               },
//     *               })
//     * @return WeChatLoginResponseDto
//     * <p>
//     * 坑：
//     * 1、在微信公众号平台注册了小程序和公众号，只有openid、没有unionid
//     * 2、要想获得unionid，必须在微信开放平台注册该小程序和公众号
//     * @throws Exception
//     */
//    public static WeChatLoginResponseDto getLoginInfo(String wxCode) throws Exception {
//        Map<String, Object> params = new HashMap<>();
//        params.put("appid", MINI_APP_ID);
//        params.put("secret", MINI_APP_SECRET);
//        params.put("js_code", wxCode);
//        params.put("grant_type", "authorization_code");
//        String resultJson = HttpUtil.get(LOGINURL, params);  // 登录小程序
//        log.info("WeChatHelper.getLoginInfo: " + resultJson);
//        // string[json] -> WeChatLoginResponseDto.class
//        ObjectMapper objectMapper = new ObjectMapper();
//        WeChatLoginResponseDto wxLoginInfo = objectMapper.readValue(resultJson, WeChatLoginResponseDto.class);  // gson.fromGson(xx,xx.class)
//        return wxLoginInfo;
//    }
//
//    /**
//     * 2、根据获phoneCode取手机号信息.
//     *
//     * @param phoneCode getPhoneNumber(e) {
//     *                  console.log("phoneCode: ", e.detail.code)
//     *                  },
//     * @return 手机号
//     */
//    public static WeChatPhoneResponseDto getPhoneInfo(String phoneCode) throws Exception {
//        String accessToken = getAccessToken(MINI_APP_ID, MINI_APP_SECRET);
//        // 请求获取用户手机号的地址
//        JSONObject jsonCode = new JSONObject();
//        jsonCode.put("code", phoneCode);
//        String result = HttpUtil.post(PHONEURL + accessToken, jsonCode.toString());
//        JSONObject jsonObject = JSONObject.parseObject(result);
//        Integer errCode = (Integer) jsonObject.get("errcode");
//        if (!Objects.equals(0, errCode)) {
//            String errMsg = (String) jsonObject.get("errmsg");
//            System.out.println("errMsg = " + errMsg);
//            return null;
//        }
//        JSONObject phoneInfo = (JSONObject) jsonObject.get("phone_info");
//        ObjectMapper objectMapper = new ObjectMapper();
//        WeChatPhoneResponseDto wxPhoneInfo = objectMapper.readValue(phoneInfo.toString(), WeChatPhoneResponseDto.class);
//        return wxPhoneInfo;
//    }
//
//    /**
//     * 3、
//     * 根据小程序的appid、secret获取小程序AccessToken
//     * 根据公众号的appid、secret获取公众号AccessToken
//     *
//     * @return accessToken
//     */
//    public static String getAccessToken(String appid, String secret) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("grant_type", "client_credential");
//        params.put("appid", appid);
//        params.put("secret", secret);
//        JSONObject tokenJson = JSONObject.parseObject(HttpUtil.get(TOKENURL, params));
//        String accessToken = (String) tokenJson.get("access_token");
//        return accessToken;
//    }
//
//    /**
//     * 4、小程序发送一次性订阅消息【只能发一次，垃圾】
//     *
//     * @param openId
//     * @param infoDto
//     */
//    public static void pushMiniProgramMessage(String openId, MessageInfoDto infoDto) {
//        String accessToken = getAccessToken(MINI_APP_ID, MINI_APP_SECRET);
//        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(accessToken)) {
//            log.error("openid not exists");
//        }
//        // 请求体,根据模版内容动态调整
//        JSONObject requestParams = new JSONObject();
//        requestParams.put("touser", openId);                // 接收订阅消息的用户openid
//        requestParams.put("template_id", MINI_TEMPLATEID);       // 订阅消息模板ID
////        requestParams.put("miniprogram_state", "developer");  // 测试
//        String nodename = "流程已到达\"" + infoDto.getNodename() + "\"节点";
//        requestParams.put("data", new JSONObject()
//                .fluentPut("thing1", new JSONObject().fluentPut("value", nodename))
//                .fluentPut("thing16", new JSONObject().fluentPut("value", infoDto.getRequestname()))
//                .fluentPut("thing14", new JSONObject().fluentPut("value", infoDto.getUsername()))
//                .fluentPut("time15", new JSONObject().fluentPut("value", infoDto.getReceivedatetime()))
//        );
//        String result = HttpUtil.post(MINI_MESSAGEURL + accessToken, requestParams.toJSONString());
//        System.out.println("result = " + result);
//    }
//
//    /**
//     * 5、微信小程序向公众号推送消息.
//     * 坑：必须是历史模版消息，不能是类目模版，巨坑，草！！！
//     *
//     * @param openId
//     * @param infoDto
//     */
//    public static void pushWeChatMessage(String openId, MessageInfoDto infoDto) {
//        String accessToken = getAccessToken(MINI_APP_ID, MINI_APP_SECRET);
//        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(accessToken)) {
//            log.error("openid not exists");
//        }
//        String nodename = "流程已到达\"" + infoDto.getNodename() + "\"节点";
//        // 封装参数,根据模版内容动态调整
//        JSONObject requestParams = new JSONObject();
//        requestParams.put("touser", openId);
//        requestParams.put("mp_template_msg", new JSONObject()
//                .fluentPut("appid", OFFICIAL_ACCOUNT_APPID)                 // 公众号appid，要求与小程序有绑定且同主体
//                .fluentPut("template_id", OFFICIAL_ACCOUNT_TEMPLATE_ID)     // 公众号消息模板id，必须是历史模版，不能是类目模版
//                .fluentPut("miniprogram", new JSONObject().fluentPut("appid", MINI_APP_ID)) // 公众号模板消息所要跳转的小程序id
//                .fluentPut("data", new JSONObject()
//                        .fluentPut("first", new JSONObject().fluentPut("value", nodename))
//                        .fluentPut("keyword1", new JSONObject().fluentPut("value", infoDto.getRequestname()))
//                        .fluentPut("keyword2", new JSONObject().fluentPut("value", infoDto.getUsername()))
//                        .fluentPut("keyword3", new JSONObject().fluentPut("value", infoDto.getReceivedatetime()))
//                        .fluentPut("remark", new JSONObject().fluentPut("value", "remark"))));
//        System.out.println("requestParams.toJSONString() = " + requestParams.toJSONString());
//        String result = HttpUtil.post(OFFICIAL_ACCOUNT_URL + accessToken, requestParams.toJSONString());
//        System.out.println("result = " + result);
//    }
//
//
//    // 后续微信支持历史模版消息，必须使用类目模版消息
//    // 坑1：小程序和公众号要想联系起来，必须在微信公众平台同时绑定，获取同一个unionid，但openid不同
//    // 坑2：必须同时保存小程序和公众号的openid、unionid在表中，因为无法通过小程序的unionid通过接口查询到公众号的openid！
//    // 坑3：用户关注/取消关注事件解析，将公众号历史关注用户、新增关注用户的openid、unionid都记录到表中
//
//
//    /**
//     * 6、微信小程序向公众号推送类目模版消息
//     *
//     * @param unionid 小程序/公众号的unionid
//     * @param infoDto
//     */
//    public static void pushWeChatTemplateMessage(String unionid, MessageInfoDto infoDto) {
//        // 1、获取公众号的openId
//        String accessToken = WeChatHelper.getAccessToken(OFFICIAL_ACCOUNT_APPID, OFFICIAL_ACCOUNT_APP_SECRET);
//        // 微信官方没有提供接口，必须第三方自己保存，比如在用户关注的时候将用户信息写到表中uf_wxgzhxx
//        // 至于你什么保存公众号的用户openid、unionid，一般是用户关注、取消关注时候进行持久化
//        String openId = getWxOpenIdByUnionId(unionid);
//        if (StringUtils.isEmpty(openId) || StringUtils.isEmpty(accessToken)) {
//            System.out.println("openid not exists");
//        }
//        // 2、封装消息模版参数,根据模版内容动态调整
//        JSONObject requestParams = new JSONObject();
//        requestParams.put("touser", openId);
//        requestParams.put("template_id", OFFICIAL_ACCOUNT_TEMPLATE_ID);
//        requestParams.put("miniprogram", new JSONObject()
//                .fluentPut("appid", MINI_APP_ID));
//        requestParams.put("data", new JSONObject()
//                .fluentPut("thing8", new JSONObject().fluentPut("value", infoDto.getRequestname().substring(0, 19))  // PS:thing8长度不能超过20
//                        .fluentPut("thing3", new JSONObject().fluentPut("value", infoDto.getUsername()))
//                        .fluentPut("time4", new JSONObject().fluentPut("value", infoDto.getReceivedatetime()))));
//        String result = HttpUtil.post(OFFICIAL_ACCOUNT_TEMPLATE_URL + accessToken, requestParams.toJSONString());
//        System.out.println("result = " + result);
//    }
//
//    /**
//     * 根据小程序的unionId获取表中对应微信关公众号的openid，查表uf_wxgzhx
//     * 坑：一定要是公众号的openid，不是小程序的openid
//     *
//     * @param unionId 小程序的unionId
//     * @return 公众号的openid
//     */
//    public static String getWxOpenIdByUnionId(String unionId) {
////        String openId = "";
////        RecordSet rs = new RecordSet();
////        String sql = String.format("select h.openid from uf_wxgzhxx h where h.unionid = '%s'", unionId);
////        rs.executeQuery(sql);
////        if (rs.next()) {
////            openId = rs.getString("openid");
////        }
////        log.writeLog("openId: ", openId);
////        return openId;
//        return "openid";
//    }
//
//
//    /**
//     * 7、将关注该微信公众号的所有历史用户信息保存到表uf_wxgzhxx
//     */
//    public static Integer syncWeChatHistoricalUsers() {
//        String accessToken = WeChatHelper.getAccessToken(OFFICIAL_ACCOUNT_APPID, OFFICIAL_ACCOUNT_APP_SECRET);
//        Map<String, Object> params = new HashMap<>();
//        params.put("access_token", accessToken);
//        // 1、微信公众号获取所有用户列表，只有openid没有unionid
//        JSONObject tokenJson = JSONObject.parseObject(HttpUtil.get(OFFICIAL_ACCOUNT_USER_INFO_URL, params));
//        JSONObject data = (JSONObject) tokenJson.get("data");
//        List<String> openids = (List<String>) data.get("openid");
//        Integer num = 0;
//        for (String openid : openids) {
//            // 查表
//            Integer counts = 0;
//            String oldUnionId = "";
////            RecordSet rs = new RecordSet();
////            String sql = String.format("select unionid, count(*) as counts from uf_wxgzhxx uw where uw.openid = '%s'", openid);
////            rs.executeQuery(sql);
////            if (rs.next()) {
////                counts = rs.getInt("counts");
////                oldUnionId = rs.getString("unionid");
////            }
//            // 2、根据微信公众号的个人openId获得对应unionId
//            String unionId = getUnionIdByOpenId(openid, accessToken);
//            if (counts >= 1 && Objects.equals(oldUnionId, unionId)) {
//                // 表中有一样的，不用操作
//                continue;
//            }
//            if (counts >= 1 && !Objects.equals(oldUnionId, unionId)) {
//                // 表中有不一样，更新即可
//                Map<String, String> updateMap = new HashMap<>();
//                updateMap.put("openid", openid);
//                updateMap.put("unionid", unionId);
//                Map<String, String> whereMap = new HashMap<>();
//                whereMap.put("openid", openid);
//                // SqlHelper.excuteUpdateSqlByMap(updateMap, whereMap, "uf_wxgzhxx", "0", 1);
//                num++;
//            }
//            if (counts < 1) {
//                // 表中没有，说明是第一个直接插入即可
//                Map<String, String> insertMap = new HashMap<>();
//                insertMap.put("openid", openid);
//                insertMap.put("unionid", unionId);
//                insertMap.put("event", "-1");
//                insertMap.put("msgtype", "-1");
//                // SqlHelper.excuteInsertSqlByMap(insertMap, "uf_wxgzhxx", "0", 1, 1);
//                num++;
//            }
//        }
//        return num;
//    }
//
//    /**
//     * 根据微信公众号的个人openId获得对应unionId，前提是在微信开放平台注册绑定，官方API
//     *
//     * @param openId      微信公众号个人openId
//     * @param accessToken 微信公众号token
//     * @return 微信公众号个人unionId
//     */
//    public static String getUnionIdByOpenId(String openId, String accessToken) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("access_token", accessToken);
//        params.put("openid", openId);
//        params.put("lang", "zh_CN");
//        JSONObject tokenJson = JSONObject.parseObject(HttpUtil.get(OFFICIAL_ACCOUNT_OPENID_URL, params));
//        return (String) tokenJson.get("unionid");
//    }
//
//    /**
//     * 8、根据需求解析对应事件：比如接入微信关注/取消关注事件,将新增用户信息保存到表uf_wxgzhxx
//     */
//    public static String saveWechatInfo(WechatInfo wechatInfo) {
//        if (Objects.equals("event", wechatInfo.getMsgType())) {         // 事件类型的监听
//            if (Objects.equals("subscribe", wechatInfo.getEvent())) {   // 关注事件的监听
//                // 插入微信公众号消息表
//                Map<String, String> insertMap = new HashMap<>();
//                insertMap.put("openid", wechatInfo.getFromUserName());
//                insertMap.put("unionid", getUnionIdByOpenId(wechatInfo.getFromUserName(), getAccessToken(OFFICIAL_ACCOUNT_APPID, OFFICIAL_ACCOUNT_APP_SECRET)));
//                insertMap.put("event", wechatInfo.getEvent());
//                insertMap.put("msgtype", wechatInfo.getMsgType());
//                // SqlHelper.excuteInsertSqlByMap(insertMap, "uf_wxgzhxx", "0", 1, 1);
//            } else if (Objects.equals("unsubscribe", wechatInfo.getEvent())) { // 取消关注事件的监听
//                // RecordSet rs = new RecordSet();
//                // String sql = String.format("DELETE FROM ecology_prod.uf_wxgzhxx WHERE openid = '%s'", wechatInfo.getFromUserName());
//                // rs.execute(sql);
//            }
//            return "微信事件执行成功";
//        }
//        return "非微信事件类型";
//    }
//}
//
