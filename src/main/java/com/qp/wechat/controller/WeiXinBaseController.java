package com.qp.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.qp.wechat.entity.ApiResult;
import com.qp.wechat.service.impl.WeiXinServiceImpl;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @author qinpeng
 * @date 2019/10/10
 */
@Controller
public class WeiXinBaseController {

    private Logger logger = LoggerFactory.getLogger(WeiXinBaseController.class);

    @Autowired
    private WeiXinServiceImpl weiXinService;

    /**
     * 通过openid获得基本用户信息
     * 详情请见: http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
     *
     * @param openid openid
     * @param lang   zh_CN, zh_TW, en
     */
    @RequestMapping(value = "/getUserInfo")
    @ResponseBody
    public ApiResult<WxMpUser> getUserInfo(HttpServletResponse response, @RequestParam(value = "openid") String openid, @RequestParam(value = "lang") String lang) {
        ApiResult<WxMpUser> apiResult = new ApiResult<>(ApiResult.FAIL_RESULT);
        try {
            WxMpUser wxMpUser = weiXinService.getUserService().userInfo(openid, lang);
            return new ApiResult<>(wxMpUser);
        } catch (WxErrorException e) {
            apiResult.setStatus(ApiResult.FAIL_RESULT);
            apiResult.setDescription(ApiResult.FAILE_DESCRIPTION);
            logger.error("getUserInfo error:{}", e);
        }
        return apiResult;
    }


    /**
     * 网页授权通过code获得基本用户信息，并跳转到对应页面
     * 详情请见: http://mp.weixin.qq.com/wiki/14/bb5031008f1494a59c6f71fa0f319c66.html
     *
     * @param code code
     * @param lang zh_CN, zh_TW, en
     */
    @RequestMapping(value = "/getOAuth2UserInfo")
    public String getOAuth2UserInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "code") String code, @RequestParam(value = "lang", defaultValue = "zh_CN") String lang) {
        try {
            WxMpOAuth2AccessToken accessToken = weiXinService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = weiXinService.getUserService()
                    .userInfo(accessToken.getOpenId(), lang);
            logger.info("wxMpUser:{}", JSON.toJSONString(wxMpUser));
            if (Objects.nonNull(wxMpUser)) {
                HttpSession session = request.getSession();
                //保存到session中，登录成功后会调用此接口，标志用户登录
                session.setAttribute("user", wxMpUser);
            }
        } catch (Exception e) {
            logger.error("getUserInfo error:{}", e);
        }
        return "/index";
    }

}
