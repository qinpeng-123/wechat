package com.qp.wechat.service.impl;

import com.qp.wechat.handler.LogHandler;
import com.qp.wechat.handler.MsgHandler;
import com.qp.wechat.service.WeiXinRouterService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.constant.WxMpEventConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


/**
 * @author qinpeng
 * @date 2019/10/10
 */
@Service
public class WeiXinRouterServiceImpl extends WxMpMessageRouter implements WeiXinRouterService {

    private Logger logger = LoggerFactory.getLogger(WeiXinRouterServiceImpl.class);

    @Autowired
    private WeiXinServiceImpl weiXinService;

    @Autowired
    private LogHandler logHandler;

    @Autowired
    private MsgHandler msgHandler;

    private WxMpMessageRouter router;


    @PostConstruct
    public void init() {
        logger.info("---开始初始化路由---");
        //刷新路由
        this.refreshRouter();
        logger.info("---初始化路由成功---");

    }

    public WeiXinRouterServiceImpl(WxMpService wxMpService) {
        super(wxMpService);
    }

    /**
     * 所有消息处理，相当于路由器
     */
    @Override
    public void refreshRouter() {
        WxMpMessageRouter newRouter = new WxMpMessageRouter(weiXinService);
        // 记录所有事件的日志
        newRouter.rule().handler(logHandler).next();

        // 默认,转发消息给客服人员
        newRouter.rule().async(false).handler(this.msgHandler).end();
//
//        // 接收客服会话管理事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxMpEventConstants.CustomerService.KF_CREATE_SESSION).handler(this.kfSessionHandler).end();
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxMpEventConstants.CustomerService.KF_CLOSE_SESSION).handler(this.kfSessionHandler).end();
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxMpEventConstants.CustomerService.KF_SWITCH_SESSION).handler(this.kfSessionHandler).end();
//
//        // 门店审核事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxMpEventConstants.POI_CHECK_NOTIFY).handler(this.storeCheckNotifyHandler).end();
//
//        // 自定义菜单事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.MenuButtonType.CLICK).handler(this.getMenuHandler()).end();
//
//        // 点击菜单连接事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.MenuButtonType.VIEW).handler(this.nullHandler).end();
//
//        // 关注事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE).handler(this.getSubscribeHandler()).end();
//
//        // 取消关注事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.UNSUBSCRIBE).handler(this.getUnsubscribeHandler()).end();
//
//        // 上报地理位置事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.LOCATION).handler(this.getLocationHandler()).end();
//
//        // 接收地理位置消息
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION).handler(this.getLocationHandler()).end();
//
//        // 扫码事件
//        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SCAN).handler(this.getScanHandler()).end();
//
//        // 默认
//        newRouter.rule().async(false).handler(msgHandler()).end();


        this.router = newRouter;
    }

    /**
     * 路由消息，对微信出来的消息进行处理
     *
     * @param wxMessage
     * @return
     */
    @Override
    public WxMpXmlOutMessage route(WxMpXmlMessage wxMessage) {
        try {
            logger.info("--处理消息--");
            return router.route(wxMessage);
        } catch (Exception e) {
            logger.error("route msg error:{}", e);
        }
        return null;
    }
}
