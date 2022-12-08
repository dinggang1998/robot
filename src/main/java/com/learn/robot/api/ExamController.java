package com.learn.robot.api;

import com.learn.robot.aspect.ApiLog;
import com.learn.robot.exception.ServiceException;
import com.learn.robot.model.Response;
import com.learn.robot.model.user.DzUser;
import com.learn.robot.util.ExamHelp;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("exam")
public class ExamController {


    @RequestMapping(method = RequestMethod.GET, value = {""})
    public String examHelp(HttpServletRequest request, Model model) {
        DzUser user = new DzUser();
        user.setUsername("考试的大冤种");
        model.addAttribute("user",user);
        return "exam/index";
    }

    @ApiOperation("考试答题系统")
    @ApiLog(description = "考试答题系统")
    @RequestMapping(value = "/help", method = RequestMethod.POST)
    public @ResponseBody Response<String> examHelp(HttpServletRequest request, @RequestBody String jsonStr,String token,String paperId,int mills) throws ServiceException , Exception {
        StringBuffer result = ExamHelp.postWithJson(paperId, token,mills);
        return Response.success(result);
    }


}
