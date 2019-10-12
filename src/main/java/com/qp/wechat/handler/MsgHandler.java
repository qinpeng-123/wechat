package com.qp.wechat.handler;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 转处理文本消息handler
 */
@Component
public class MsgHandler extends AbstractHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        String content = "您好，欢迎关注公众号！";
        return WxMpXmlOutMessage
                .TEXT().fromUser(wxMessage.getToUser())
                .toUser(wxMessage.getFromUser()).content(content + "[您输入的内容是]：" + wxMessage.getContent()).build();
    }
}
