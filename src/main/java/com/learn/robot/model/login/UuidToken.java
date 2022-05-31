package com.learn.robot.model.login;


import com.learn.robot.util.AESEUtils;

import java.util.UUID;

/**
 * 针对图形验证码增加放绕过验证实体
 */
public class UuidToken {

    private String uuid;

    private String uuidPlain;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getUuidPlain() {
        return uuidPlain;
    }

    public void setUuidPlain(String uuidPlain) {
        this.uuidPlain = uuidPlain;
    }

    public UuidToken() {
        String uuidStr = UUID.randomUUID().toString();
        this.uuid = uuidStr;
        this.uuidPlain = AESEUtils.encrypt(uuidStr);
    }
}
