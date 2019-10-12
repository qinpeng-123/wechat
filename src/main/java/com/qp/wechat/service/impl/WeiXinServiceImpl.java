package com.qp.wechat.service.impl;


import com.qp.wechat.service.WeiXinRouterService;
import com.qp.wechat.service.WeiXinService;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * @author qinpeng
 * @date 2019/10/10
 */
@Service
public class WeiXinServiceImpl extends WxMpServiceImpl implements WeiXinService {

    @Autowired
    private WxMpConfigStorage wxMpConfigStorage;


    /**
     * 注入配置文件
     */
    @PostConstruct
    public void init() {
        setWxMpConfigStorage(wxMpConfigStorage);
    }


}
