package com.learn.robot.util;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 继承自己的MyMapper
 * TODO FIXME 特别注意，该接口不能被扫描到，否则会出错
 */
public interface MyMapper<T> extends Mapper<T>, BaseMapper<T>,
        ConditionMapper<T>, IdsMapper<T>, InsertListMapper<T> {
}
