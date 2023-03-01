package com.learn.robot.service.exam.impl;

import com.learn.robot.mapper.ExamMapper;
import com.learn.robot.model.Exam;
import com.learn.robot.service.exam.ExamService;
import com.learn.robot.util.EmailUtil;
import com.learn.robot.util.ExamHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class ExamServiceImpl implements ExamService {


    @Autowired
    ExamMapper examMapper;

    @Autowired
    EmailUtil emailUtil;


    @Override
    public StringBuffer examHelp(String token,String paperId,String mills) throws Exception {
        StringBuffer text=new StringBuffer();
//        Exam exam=new Exam();
//        exam.setCreateTime(new Date());
//        exam.setText("=====paperId:"+paperId+"=====token:"+token+"=====mills:"+mills);
//        examMapper.insertSelective(exam);
        emailUtil.sendMessage("1244910580@qq.com", "开始考试",  "paperId:"+paperId+"，token:"+token+"，mills:"+mills);
        text=ExamHelp.postWithJson(paperId, token,mills);
        emailUtil.sendMessage("1244910580@qq.com", "考试结果",  text.toString());
        return text;
    }

}
