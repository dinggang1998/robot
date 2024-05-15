package com.learn.robot;

import com.alibaba.fastjson.JSONObject;
import com.learn.proxy.*;
import com.learn.robot.mapper.DzUserMapper;
import com.learn.robot.model.Response;
import com.learn.robot.service.user.UserService;
import com.learn.robot.util.*;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RobotApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@SpringBootTest
public class RobotApplicationTest extends TestCase {

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    DzUserMapper dzUserMapper;

    @Autowired
    UserService userService;

    @Value("${spring.encrypt.publicKey}")
    String publicKey;

    public String patchFile = "/Users/dinggang/Desktop/bto_filter_propty.sql";//补丁文件,由eclipse svn plugin生成(非必须)

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
    public void createInsertSql() {
        String fileName = "example.sql";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://10.124.143.154:22006/d_delvs",
                    "d_delvs",
                    "LfT1wR20j8TaH6QJs7sq");
            System.out.println("成功连接到数据库！");
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            List<String> tableList = new ArrayList<>();
            tableList = dzUserMapper.getTables();
//            tableList.add("bto_contract");
            // 过滤出以"bto_"或者"td_"开头的表格名
            tableList = tableList.stream().filter(o -> (o.startsWith("bto_") || o.startsWith("td_"))).collect(Collectors.toList());
            for (String tableName : tableList) {
                StringBuffer sb = new StringBuffer();
                Map<String, String> map = dzUserMapper.getCreateTableSql(tableName);
                sb.append("###############################表" + tableName + "的脚本###############################\n");
                String createTable = map.get("Create Table");
                sb.append("DROP TABLE IF EXISTS " + map.get("Table")).append(";\n");
                sb.append(createTable + ";\n");
                sb.append("DELETE FROM " + tableName + " WHERE 1=1;\n");
                String column = dzUserMapper.getColumnList(tableName);
                String coalesceColumn = "coalesce(" + column.replaceAll(",", ",'nu'), coalesce(") + ")";
                List<String> valuesList = dzUserMapper.getTableValue(coalesceColumn, tableName);
                StringBuffer insertSql = new StringBuffer();
                insertSql.append("INSERT INTO " + tableName + " (" + column + ") VALUES ").append("\n");
                valuesList.forEach(o -> insertSql.append("('").append(o.replaceAll("`", "'").replaceAll("'nu'", "null")).append("'),\n"));
                sb.append(insertSql.delete(insertSql.length() - 2, insertSql.length()).append(";\n"));
                writer.write(sb.toString());
            }
            writer.close();
            connection.close();
            System.out.println("SQL文件已创建并写入内容：" + fileName);
        } catch (ClassNotFoundException e) {
            System.err.println("未找到数据库驱动程序类");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("无法连接到数据库");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("无法创建或写入文件：" + e.getMessage());
        }
    }

    @Test
    public void dingDingTest() {
        String message =
        "@15861334359  \n" +
                "### 这是一个标题\n" +
                        "#### 这是正文内容\n" +
                        "#### 这是正文内容\n" +
                        "#### 这是正文内容\n";
        String res = DingTalkUtil.postWithJson("test", message, true);
//        String token = DingTalkUtil.getToken();
        System.out.println(res);
    }


    @Test
    public void test5() {

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
        emailUtil.sendMessageCarryFile(to, title, content, files);
    }

    @Test
    public void test9() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("1", "ding11231232132131232");
        serect(jsonObject);
    }


    public Response<String> serect(JSONObject jsonObject) throws Exception {
        log.info("=======>加密前:{}", jsonObject);
        PublicKey publicKey1 = RSAUtils.string2PublicKey(publicKey);
        byte[] publicEncrypt = RSAUtils.publicEncrypt(jsonObject.toString().getBytes(), publicKey1);
        String byte2Base64 = RSAUtils.byte2Base64(publicEncrypt);
        log.info("=======>加密后:{}", byte2Base64);
        return Response.success(byte2Base64);
    }

    @Test
    public void test1() {
        ChanelFactory factory = new ChanelFactory();
        XiaoHongSellProxy proxy = new XiaoHongSellProxy(factory);
        proxy.sellPerfume(1999.99);
    }


    @Test
    public void test2() {
        ChanelFactory chanelFactory = new ChanelFactory();
        SellProxyFactory sellProxyFactory = new SellProxyFactory(chanelFactory);
        SellPerfume sellPerfume = (SellPerfume) Proxy.newProxyInstance(chanelFactory.getClass().getClassLoader(),
                chanelFactory.getClass().getInterfaces(),
                sellProxyFactory);
        sellPerfume.sellPerfume(1999.99);
    }


    @Test
    @Transactional
    public void test3() {
        RedWineFactory redWineFactory = new RedWineFactory();
        SellProxyFactory sellProxyFactory = new SellProxyFactory(redWineFactory);
        SellWine sellWine = (SellWine) Proxy.newProxyInstance(redWineFactory.getClass().getClassLoader(),
                redWineFactory.getClass().getInterfaces(), sellProxyFactory);
        sellWine.sellWine(1231312);
    }

    @Test
    public void test4() {
        String s = null;
        if (!(2 == 3) || !(s.equals("1"))) {
            System.out.println("1");
        }
    }

}
