package com.learn.robot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.learn.proxy.*;
import com.learn.robot.mapper.DzUserMapper;
import com.learn.robot.model.Response;
import com.learn.robot.model.user.DzUser;
import com.learn.robot.service.user.UserService;
import com.learn.robot.util.*;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RobotApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@SpringBootTest
public class RobotApplicationTests extends TestCase {

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    DzUserMapper dzUserMapper;

    public  String patchFile="/Users/dinggang/Desktop/bto_filter_propty.sql";//补丁文件,由eclipse svn plugin生成(非必须)

    @Test
    public void insertQuick() throws IOException {
        FileInputStream f = new FileInputStream(patchFile);
        BufferedReader dr = new BufferedReader(new InputStreamReader(f, "GBK"));
        String line;
        while ((line = dr.readLine()) != null) {
            if (!line.isEmpty()) {
                System.out.println(line);
                dzUserMapper.insertSql(line);
            }
        }
        dr.close();
    }
    @Test
    public void createCreateTableSql() {
        List<String> tableList = dzUserMapper.getAllTable();
        for(String tableName:tableList){
//            System.out.println(tableName.replaceAll("SHOW CREATE","DROP"));
            Map<String, String> test = dzUserMapper.getTable(tableName);
            String createTable = test.get("Create Table");
            System.out.println(createTable+";");
            System.out.println("#######################################");
        }
    }


    @Test
    public void createInsertSql() {
//        List<String> tableList=new ArrayList<>();
//        tableList.add("a_env");
        List<String> tableList = dzUserMapper.getTables();
        tableList =tableList.stream().filter(o -> (o.startsWith("bto_") || o.startsWith("td_"))&& !o.equals("bto_filter_propty")).collect(Collectors.toList());
        System.out.println(tableList);
        for (String tableName : tableList) {
            List<String> columnList = dzUserMapper.getColumnList(tableName);
            List<String> columnListNew = new ArrayList<>();
            for (String column : columnList) {
                columnListNew.add("coalesce(" + column + ",'')");
            }
            String column = String.join(", ", columnList);
            List<String> valuesList = dzUserMapper.getTableValue(String.join(", ", columnListNew).replaceAll(", ", "," +
                    "'ACD, ACD',"), tableName);
            System.out.println("###############################插入表" + tableName + "的数据###############################");
            String deleteSql="DELETE FROM " + tableName + " WHERE 1=1;";
            System.out.println(deleteSql);
//            dzUserMapper.deleteSql(deleteSql);
            for (String value : valuesList) {
                String insertSql="INSERT INTO i_delvs.`" + tableName + "` (" + column + ") VALUES (" + value.replaceAll("ACD", "'").replaceAll("''","null") + ");";
                System.out.println(insertSql);
//                dzUserMapper.insertSql(insertSql);
            }
        }
    }

    @Test
    public void dingDingTest() {

        String message = "### 这是一个标题\n" +
                "#### 这是正文内容\n" +
                "#### 这是正文内容\n" +
                "#### 这是正文内容\n";
        String res = DingTalkUtil.postWithJson("test", message, false);
//        String res = DingTalkUtil.getToken();
        System.out.println(res);
    }


    @Test
    public void testsss() {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("1", 1);
        HashMap<Object, Object> testttt1 = new HashMap<>();
        testttt1.put("3", 1);

        HashMap<Object, Object> testttt2 = new HashMap<>();
        testttt1.put("2", 1);

        ArrayList<Object> objects = new ArrayList<>();
        objects.add(testttt1);
        objects.add(testttt2);
        map.put("2", objects);


        JSONObject resultDev = JSON.parseObject(JSONObject.toJSONString(map), JSONObject.class);
        log.info(String.valueOf(resultDev));

        JSONObject returnValue = (JSONObject) JSONObject.toJSON(map);

        JSONArray resultList = resultDev.getJSONArray("2");
        if (resultList != null && !resultList.isEmpty()) {
            log.info("!211");

            JSONObject resultDevelop = (JSONObject) (resultList.get(0));
            log.info(String.valueOf(resultDevelop));

        }
        JSONObject object = new JSONObject();
        JSONObject tempItem = (JSONObject) object.clone();

        Map<Object, String> maps = new LinkedHashMap<>();
        maps.put("1", "2");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1", "2");

        JSONArray s = new JSONArray();
        s.add(jsonObject);

        log.info(String.valueOf(maps));

        log.info(String.valueOf(jsonObject));

        log.info(String.valueOf(s.get(0)));
        JSONObject ssss = (JSONObject) JSON.toJSON(s.get(0));
//        JSONObject sss=(JSONObject) s.get(0);
//        s.get(0);


    }

    @Test
    public void sendStringEmail() {
        // 测试文本邮件发送（无附件）
        String to = "zhufangyue@qq.com";
        String title = "你好呀猪猪猪";
        String content = "啦啦啦啦啦";
        emailUtil.sendMessage(to, title, content);
    }

    @Test
    public void sendFileEmail() {
        // 测试单个附件邮件发送
        String to = "fyzhu@che300.com";
        String title = "你好呀猪猪猪！！！！！";
        String content = "啦啦啦啦啦！！！！！！！";
        File file = new File("/Users/dinggang/Downloads/td_s_light_item.sql");
        System.out.println(file);
        emailUtil.sendMessageCarryFile(to, title, content, file);
    }

    @Test
    public void sendFilesEmail() {
        // 测试多个附件邮件发送
        String to = "1354720990@qq.com";
        String title = "多个附件邮件发送测试";
        String content = "多个附件邮件发送测试";
        File[] files = new File[2];
        files[0] = new File("C:\\Users\\root\\Desktop\\配置邮箱\\1.png");
        files[1] = new File("C:\\Users\\root\\Desktop\\配置邮箱\\2.png");
//        emailUtil.sendMessageCarryFile(to, title, content, files);
    }


    @Autowired
    UserService userService;


    @Test
    public void test3() {
        List<DzUser> userList = dzUserMapper.selectAll();
        System.out.println(userList.get(0).toString());
    }

    @Test
    public void test9() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1", "ding11231232132131232");
        serect(jsonObject);
    }

    @Value("${spring.encrypt.publicKey}")
    String publicKey;

    public Response<String> serect(JSONObject jsonObject) throws Exception {
        log.info("=======>加密前:{}", jsonObject);
        PublicKey publicKey1 = RSAUtils.string2PublicKey(publicKey);
        byte[] publicEncrypt = RSAUtils.publicEncrypt(jsonObject.toString().getBytes(), publicKey1);
        String byte2Base64 = RSAUtils.byte2Base64(publicEncrypt);
        log.info("=======>加密后:{}", byte2Base64);
        return Response.success(byte2Base64);
    }

    @Test
    public void test11111() {
        ChanelFactory factory = new ChanelFactory();
        XiaoHongSellProxy proxy = new XiaoHongSellProxy(factory);
        proxy.sellPerfume(1999.99);
    }


    @Test
    public void test4324234() {
        ChanelFactory chanelFactory = new ChanelFactory();
        SellProxyFactory sellProxyFactory = new SellProxyFactory(chanelFactory);
        SellPerfume sellPerfume = (SellPerfume) Proxy.newProxyInstance(chanelFactory.getClass().getClassLoader(),
                chanelFactory.getClass().getInterfaces(),
                sellProxyFactory);
        sellPerfume.sellPerfume(1999.99);
    }


    @Test
    @Transactional
    public void test12321() {
        RedWineFactory redWineFactory = new RedWineFactory();
        SellProxyFactory sellProxyFactory = new SellProxyFactory(redWineFactory);
        SellWine sellWine = (SellWine) Proxy.newProxyInstance(redWineFactory.getClass().getClassLoader(),
                redWineFactory.getClass().getInterfaces(), sellProxyFactory);
        sellWine.sellWine(1231312);
    }

    @Test
    public void test13123() {
        String s = null;

        if (!(2 == 3) || !(s.equals("1"))

        ) {
            System.out.println("1");
        }
    }

}
