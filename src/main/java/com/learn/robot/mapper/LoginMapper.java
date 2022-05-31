package com.learn.robot.mapper;

import com.learn.robot.domain.User;
import com.learn.robot.util.MyMapper;
import org.apache.ibatis.annotations.*;
import java.util.List;


@Mapper
public interface LoginMapper extends MyMapper<User> {

   @Select("select * from steven_user where user_state<>'2HB' and username=#{username}")
   List<User> getUserByUserName(@Param("username") String username);

   @Update("UPDATE steven_user SET LOGINATTEMPS=#{loginattemps}, LOCK_TYPE=#{lock_type},login_time=current_time WHERE USER_NO=#{user_no}")
   int updateLoginattempsAndLock(@Param("loginattemps") String loginattemps, @Param("lock_type") String lock_type, @Param("user_no") String user_no);

}