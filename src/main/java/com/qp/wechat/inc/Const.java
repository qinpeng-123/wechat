package com.qp.wechat.inc;


public class Const {

    /**
     * 获得accesstoken url
     */
    public final static String WEI_XIN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    /**
     * 通过accesstoken 拉取用户信息url
     */
    public final static String WEI_XIN_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
}
