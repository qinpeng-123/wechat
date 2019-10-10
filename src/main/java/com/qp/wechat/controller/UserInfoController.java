package com.qp.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserInfoController {

    @GetMapping(value = "/user")
    public String uerinfo(HttpServletRequest request, HttpServletResponse response){
        return "/userInfo";
    }
}
