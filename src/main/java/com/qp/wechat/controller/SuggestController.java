package com.qp.wechat.controller;

import com.qp.wechat.entity.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author qinpeng
 * 咨询建议相关
 */
@Controller
public class SuggestController {
    private final static Logger logger = LoggerFactory.getLogger(SuggestController.class);

    @GetMapping(value = "/suggest")
    public String suggest() {
        //咨询建议页面
        return "suggest";
    }

    @PostMapping(value = "/suggest")
    @ResponseBody
    public ApiResult saveSuggest(String phone, String name, String suggest) {
        logger.info("name:{},phone:{},suggest:{}", name, phone, suggest);
        ApiResult apiResult = new ApiResult(ApiResult.SUCCESS_RESULT);
        //咨询建议页面
        return apiResult;
    }
}
