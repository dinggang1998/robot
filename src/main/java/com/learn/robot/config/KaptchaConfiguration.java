package com.learn.robot.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KaptchaConfiguration {

    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        com.google.code.kaptcha.impl.DefaultKaptcha defaultKaptcha = new com.google.code.kaptcha.impl.DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border.color", "105,179,90");
        //文字颜色
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        //宽度
        properties.setProperty("kaptcha.image.width", "180");
        //高度
        properties.setProperty("kaptcha.image.height", "80");
        //字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "50");
        //无边框
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.session.key", "code");
        //长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        //字符间距
        properties.setProperty("kaptcha.textproducer.char.space", "5");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}
