package com.learn.robot.model.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;

/**
 * @author Steven Ding
 * @since 2022-06-06
 */
@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = false)
@TableName("dz_user")
public class DzUser extends Model<DzUser> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    //oracle数据库使用此方法插入序列
    //@SequenceGenerator(name = "SEQ_CTG_USER", sequenceName = "SEQ_CTG_USER")
    @Id
    private String id;

    /**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 用户状态（ 2HA 正常，2HB 注销）
     */
    private String userState;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 登录尝试次数
     */
    private String loginattemps;

    /**
     * 锁定类型（0非锁，1锁定，2冻结）
     */
    private String lockType;

    /**
     * 锁定时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String lockTimes;

    /**
     * 登陆时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
