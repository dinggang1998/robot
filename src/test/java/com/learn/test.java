package com.learn;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.learn.robot.model.user.DzUser;
import com.learn.test2.Fruit;
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

        int a=5;
        System.out.println(a);

        int b=++a;

        System.out.println(a);
        System.out.println(b);

        int c=a++;
        System.out.println(a);
        System.out.println(c);

        Stream<DzUser> userStream=users.stream();
        Map<String, List<DzUser>> collect = userStream.collect(Collectors.groupingBy(
                DzUser::getEmail
        ));
        users.stream().forEach(s->
            System.out.println(s.getId())
        );
        collect.forEach((k,v)->{System.out.println(k+" "+v);});
        System.out.println(collect);
        final List<DzUser> users1 = collect.get("23@qq");
        System.out.println(users1);
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(collect));
        System.out.println(jsonObject);

        JSONArray objects = JSONObject.parseArray(JSON.toJSONString(users));
        System.out.println(objects);


        int aa=1;
        if(a!=2){
            System.out.println("test1");
        }
        else if(a!=3){
            System.out.println("test2");
        }
        else if(a!=4){
            System.out.println("test3");
        }
        else{
            System.out.println("test4");
        }

//        test2.Apple fruit=new test2.Apple();

        for(int aaa = 1;aaa<6;aaa++){
            StringBuffer test=new StringBuffer();
            for(int bbb = 1;bbb<=aaa;bbb++){
                test.append(aaa+""+bbb+" ");
            }
            System.out.println(test);
        }



    }

}
