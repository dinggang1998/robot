package com.learn.robot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping(method = RequestMethod.GET, value = {"", "index"})
    public String getAll(Model model) {
        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = {"login"})
    public String getAlls(Model model) {
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

}
