package com.qp.wechat.controller;

import com.qp.wechat.service.WeiXinRouterService;
import com.qp.wechat.service.impl.WeiXinRouterServiceImpl;
import com.qp.wechat.service.impl.WeiXinServiceImpl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author qinpeng
 * @date 2019/10/10
 */
@Controller
public class MainController {

    @Autowired
    private WeiXinServiceImpl weiXinService;

    @Autowired
    private WeiXinRouterServiceImpl weiXinRouterService;

    @Autowired
    private WxMpConfigStorage configStorage;

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    /**
     * 微信公众号webservice主服务接口，提供与微信服务器的信息交互
     */
    @RequestMapping(value = "/weixin")
    public void wechatCore(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);

            String signature = request.getParameter("signature");
            String nonce = request.getParameter("nonce");
            String timestamp = request.getParameter("timestamp");

            if (!this.weiXinService.checkSignature(timestamp, nonce, signature)) {
                // 消息签名不正确，说明不是公众平台发过来的消息
                response.getWriter().println("非法请求");
                return;
            }

            String echoStr = request.getParameter("echostr");
            if (StringUtils.isNotBlank(echoStr)) {
                // 说明是一个仅仅用来服务器验证的请求，回显echostr，即可接入
                String echoStrOut = String.copyValueOf(echoStr.toCharArray());
                response.getWriter().println(echoStrOut);
                return;
            }

            String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type"))
                    ? "raw"
                    : request.getParameter("encrypt_type");

            if ("raw".equals(encryptType)) {
                // 明文传输的消息
                WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
                WxMpXmlOutMessage outMessage = weiXinRouterService.route(inMessage);
                if (outMessage == null) {
                    response.getWriter().write("");
                } else {
                    response.getWriter().write(outMessage.toXml());
                }
                return;
            }

            if ("aes".equals(encryptType)) {
                // 是aes加密的消息
                String msgSignature = request.getParameter("msg_signature");
                WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(
                        request.getInputStream(), this.configStorage, timestamp, nonce,
                        msgSignature);
                this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
                WxMpXmlOutMessage outMessage = this.weiXinRouterService.route(inMessage);
                if (outMessage == null) {
                    response.getWriter().write("");
                } else {
                    response.getWriter().write(outMessage.toEncryptedXml(this.configStorage));
                }

                return;
            }

            response.getWriter().println("不可识别的加密类型");
        } catch (Exception e) {
            logger.error("weixin main error:{}", e);
        }
    }

}
