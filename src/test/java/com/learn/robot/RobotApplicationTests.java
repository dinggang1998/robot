package com.learn.robot;

import com.learn.robot.util.DingTalkUtil;
import com.learn.robot.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
//@SpringBootTest(classes = RobotApplication.class)
@Slf4j
class RobotApplicationTests {

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
    void sendStringEmail() {
        // 测试文本邮件发送（无附件）
        String to = "zhufangyue@qq.com";
        String title = "你好呀猪猪猪";
        String content = "啦啦啦啦啦";
        emailUtil.sendMessage(to, title, content);
    }

    @Test
    void sendFileEmail() {
        // 测试单个附件邮件发送
        String to = "1244910580@qq.com";
        String title = "你好呀猪猪猪";
        String content = "啦啦啦啦啦";
        File file = new File("/Users/dinggang/Downloads/td_s_light_item.sql");
        System.out.println(file);
        emailUtil.sendMessageCarryFile(to, title, content, file);
    }

    @Test
    void sendFilesEmail() {
        // 测试多个附件邮件发送
        String to = "1354720990@qq.com";
        String title = "多个附件邮件发送测试";
        String content = "多个附件邮件发送测试";
        File[] files = new File[2];
        files[0] = new File("C:\\Users\\root\\Desktop\\配置邮箱\\1.png");
        files[1] = new File("C:\\Users\\root\\Desktop\\配置邮箱\\2.png");
//        emailUtil.sendMessageCarryFile(to, title, content, files);
    }

}
