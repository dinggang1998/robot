package com.learn.robot;

import com.learn.robot.mapper.UserMapper;
import com.learn.robot.domain.LoginUser;
import com.learn.robot.service.user.UserService;
import com.learn.robot.util.*;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.io.File;
import java.util.List;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RobotApplication.class,webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RobotApplicationTests extends TestCase {

    @Autowired
    private EmailUtil emailUtil;

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

    @Autowired
    UserMapper userMapper;

    @Test
    public void test3(){
        List<LoginUser> userList= userMapper.selectAll();
        System.out.println(userList.get(0).toString());
    }

}
