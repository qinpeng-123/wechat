package com.qp.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.qp.wechat.inc.Const;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.weixin4j.WeixinException;
import org.weixin4j.http.HttpsClient;
import org.weixin4j.http.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class WeixinLoginController {
    private final static Logger logger = LoggerFactory.getLogger(WeixinLoginController.class);

    @GetMapping(value = "/login")
    public String login(){
       return "/login";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response) throws WeixinException {
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        if (!StringUtils.isEmpty(phone) && !StringUtils.isEmpty(password)){
            if (phone.equals("15907259676") && password.equals("123456")){
                return "success";
            } else {
                return "error";
            }
        } else {
            return "error";
        }
    }

    @RequestMapping(value = "/getOpenId", method = RequestMethod.GET)
    public String getOpenId(HttpServletRequest request, HttpServletResponse response) throws WeixinException {
        HttpsClient http = new HttpsClient();
        //网页授权回调code
        String code = request.getParameter("code");
        logger.info("getOpenId code:{}", code);
        if (org.apache.commons.lang.StringUtils.isNotEmpty(code)) {
            //获取网页授权token，与基础的token不同
            String url = " https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
            url = url.replace("APPID", Const.WEI_XIN_APP_ID).replace("CODE", code).replace("SECRET", Const.WEI_XIN_SECRET);
            Response result = http.get(url);
            if (Objects.nonNull(result)) {
                JSONObject jsonObject = result.asJSONObject();
                HttpSession session = request.getSession();
                session.setAttribute("openid", jsonObject.getString("openid"));

                logger.info("session:{}", session.getAttribute("openid"));
            }
        }
        return "/index";
    }


    @RequestMapping(value = "/loginOut")
    public String loginOut(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String  openid = (String) session.getAttribute("openid");
        if (StringUtils.isNotEmpty(openid)){
            session.removeAttribute("openid");
        }
        return "/index";
    }


}
