package com.learn.robot.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "STEVEN_USER")
public class LoginUser {

    @Id
    private String id;

    private String username;

    private String password;

}
