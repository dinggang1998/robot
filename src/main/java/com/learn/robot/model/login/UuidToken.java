package com.learn.robot.model.login;


import com.learn.robot.util.AESEUtils;
import lombok.Data;

import java.util.UUID;

/**
 * 针对图形验证码增加放绕过验证实体
 */
@Data
public class UuidToken {

    private String uuid;

    private String uuidPlain;

    public UuidToken() {
        String uuidStr = UUID.randomUUID().toString();
        this.uuid = uuidStr;
        this.uuidPlain = AESEUtils.encrypt(uuidStr);
    }
}
