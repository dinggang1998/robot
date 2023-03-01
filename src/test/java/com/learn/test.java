package com.learn;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.learn.robot.model.user.DzUser;
import com.sun.javafx.scene.transform.TransformUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class test {

    @Test
    public void test() {
        List<DzUser> users = new ArrayList<>();
        users.add(new DzUser("1","ding","23@qq"));
        users.add(new DzUser("3","dsfs","23@qq"));
        users.add(new DzUser("2","dinsfdsg","12@qq"));
        users.add(new DzUser("4","dindddddg","12@qq"));

        Stream<DzUser> userStream=users.stream();
        Map<String, List<DzUser>> collect = userStream.collect(Collectors.groupingBy(
                DzUser::getEmail
        ));
        collect.forEach((k,v)->{System.out.println(k+" "+v);});
        System.out.println(collect);
        final List<DzUser> users1 = collect.get("23@qq");
        System.out.println(users1);
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(collect));
        System.out.println(jsonObject);

        JSONArray objects = JSONObject.parseArray(JSON.toJSONString(users));
        System.out.println(objects);




    }

}
