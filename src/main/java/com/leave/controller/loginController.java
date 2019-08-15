package com.leave.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class loginController {
    private final static Logger logger = Logger.getLogger(webService.class);

    @RequestMapping(path = "/ForgetPwd")
    public ModelAndView index(){
        System.out.println("||");
        ModelAndView modelAndView = new ModelAndView("/Login/ForgetPwd");
        return modelAndView;
    }

}
