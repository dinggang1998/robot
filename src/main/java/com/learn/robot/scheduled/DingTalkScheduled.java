package com.learn.robot.scheduled;

import com.learn.robot.util.DingTalkUtil;
import com.learn.robot.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@EnableScheduling
@Component
@Configuration
class DingTalkScheduled {

    @Autowired
    EmailUtil emailUtil;

    //@Scheduled(cron = "0/2 * * * * ?")
    public void test1() {
        log.info("=========进入定时");
        String message = "### 这是一个标题111\n" +
                "#### 这是正文内容\n" +
                "#### 这是正文内容\n" +
                "#### 这是正文内容\n";
        String res = DingTalkUtil.postWithJson("test", message, false);
    }

//    @Scheduled(cron = "0/2 * * * * ?")
//    public void test2(){
//        log.info("=========进入定时");
//        // 测试文本邮件发送（无附件）
//        String to = "yuanym@asiainfo.com";
//        String title = "文本邮件发送测试";
//        String content = "文本邮件发送测试";
//        emailUtil.sendMessage(to, title, content);
//    }
}
