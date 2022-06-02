package com.learn.robot.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "USER")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    //    @SequenceGenerator(name = "SEQ_CTG_USER", sequenceName = "SEQ_CTG_USER")
    private String id;

    @Id
    private String user_no;

    private String username;//使用人中文名

    private String pwd;

    private String phone;//手机号码

    private String user_state;//用户当前状态：2HA 正常，2HB 注销（拆机）

    private String email;

    private String loginattemps;

    private String lock_type;

    private String lock_times;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time = new Date();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date update_time = new Date();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date login_time = new Date();

}
