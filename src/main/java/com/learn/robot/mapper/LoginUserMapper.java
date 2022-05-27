package com.learn.robot.mapper;


import com.learn.robot.domain.LoginUser;
import com.learn.robot.util.MyMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface LoginUserMapper extends MyMapper<LoginUser> {

    @Select("select * from steven_user")
    List<LoginUser> getUserList();

}
