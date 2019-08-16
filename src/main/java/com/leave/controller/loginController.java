package com.leave.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class loginController {

    @RequestMapping(path = "/index")
    public ModelAndView index(String username){
        System.out.println("||");
        ModelAndView modelAndView = new ModelAndView("redirect:/view/Login/index.jsp");
        return modelAndView;
    }

}
