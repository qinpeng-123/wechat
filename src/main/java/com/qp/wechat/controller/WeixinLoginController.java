package com.qp.wechat.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class WeixinLoginController {
    private final static Logger logger = LoggerFactory.getLogger(WeixinLoginController.class);

    @GetMapping(value = "/login")
    public String login() {
        return "/login";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        if (StringUtils.isNotEmpty(phone) && StringUtils.isNotEmpty(password)) {
            if (phone.equals("15907259676") && password.equals("123456")) {
                return "success";
            } else {
                return "error";
            }
        } else {
            return "error";
        }
    }


    @RequestMapping(value = "/loginOut")
    public String loginOut(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (Objects.nonNull(user)) {
            session.removeAttribute("user");
        }
        return "/index";
    }


}
