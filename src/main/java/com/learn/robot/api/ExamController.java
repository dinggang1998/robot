package com.learn.robot.api;

import com.learn.robot.aspect.ApiLog;
import com.learn.robot.aspect.Login;
import com.learn.robot.exception.ServiceException;
import com.learn.robot.model.Response;
import com.learn.robot.model.user.DzUser;
import com.learn.robot.service.exam.ExamService;
import com.learn.robot.util.ExamHelp;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("")
public class ExamController {

    @Resource
    ExamService examService;

    @RequestMapping(method = RequestMethod.GET, value = {"exam"})
    public String examHelp(HttpServletRequest request, Model model) {
        DzUser user=new DzUser();
        user.setUsername("考试的大冤总");
        model.addAttribute("user",user);
        return "exam/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = {"examHelp"})
    public String examHelpNew(HttpServletRequest request, Model model) {
        DzUser user=new DzUser();
        user.setUsername("考试的大冤总");
        model.addAttribute("user",user);
        return "exam/help";
    }

    @ApiOperation("考试答题系统")
    @ApiLog(description = "考试答题系统")
    @RequestMapping(value = "exam/help", method = RequestMethod.POST)
    public @ResponseBody Response<String> examHelp(HttpServletRequest request, @RequestBody String jsonStr,String token,String paperId,String mills) throws ServiceException , Exception {
        StringBuffer result = examService.examHelp(token, paperId,mills);
        return Response.success(result);
    }


}
