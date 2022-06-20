package com.learn.robot.api;

import com.learn.robot.model.user.DzUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequestMapping("/")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET, value = {"", "index"})
    public String index(HttpServletRequest request,Model model) {
        DzUser user = (DzUser) request.getSession().getAttribute("User");
        if(user==null){
            return "403";
        }
        model.addAttribute("user",user);
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = {"login"})
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value = {"logout"})
    public String logout(HttpServletRequest request,Model model) {
        request.getSession().removeAttribute("User");
        return "login";
    }

    @RequestMapping(method = RequestMethod.GET, value = "403")
    public String accessDenied() {
        return "403";
    }

    @RequestMapping(method = RequestMethod.GET, value = "404")
    public String notFound() {
        return "404";
    }

    @RequestMapping(method = RequestMethod.GET, value = "500")
    public String systemError() {
        return "500";
    }

    @RequestMapping(method = RequestMethod.GET, value = "index1")
    public String index1() {
        return "index1";
    }
}
