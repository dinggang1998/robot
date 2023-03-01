package com.learn.robot.service.exam;

import com.learn.robot.exception.ServiceException;
import com.learn.robot.model.user.DzUser;

import java.util.List;

public interface ExamService {

    StringBuffer examHelp(String token,String paperId,String mills) throws Exception;

}
