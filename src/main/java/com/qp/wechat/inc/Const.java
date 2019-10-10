package com.qp.wechat.inc;

import org.weixin4j.Configuration;

public class Const {
    public final static String WEI_XIN_APP_ID = Configuration.getProperty("weixin4j.oauth.appid");
    public final static String WEI_XIN_SECRET = Configuration.getProperty("weixin4j.oauth.secret");
}
