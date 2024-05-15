package com.learn.robot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.robot.model.user.DzUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author Steven Ding
 * @since 2022-06-06
 */
@Mapper
public interface DzUserMapper extends BaseMapper<DzUser> {

    @Select("SELECT CONCAT('SHOW CREATE TABLE `', table_name, '`;') as tableName FROM information_schema.tables WHERE" +
            " table_schema = #{name}")
    List<String> getAllTable(String name);

    @Select("SHOW TABLES")
    List<String> getTables();

    @Select("SHOW CREATE TABLE ${tableName}")
    Map<String, String> getCreateTableSql(String tableName);

    @Select("select GROUP_CONCAT(column_name) from information_schema.columns where table_name = #{tableName}")
    String getColumnList(String tableName);

    @Select("select CONCAT_WS('`,`',${column}) from ${tableName}")
    List<String> getTableValue(String column, String tableName);

    @Delete("${sql}")
    void deleteSql(String sql);

    @Insert("${sql}")
    void insertSql(String sql);
}