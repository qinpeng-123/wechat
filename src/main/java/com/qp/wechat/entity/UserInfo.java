package com.qp.wechat.entity;

import lombok.Data;

/**
 * 微信用户实体
 */
@Data
public class UserInfo {
    /**
     * {
     * "openid":" OPENID",
     * " nickname": NICKNAME,
     * "sex":"1",
     * "province":"PROVINCE"
     * "city":"CITY",
     * "country":"COUNTRY",
     * "headimgurl":       "http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
     * "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
     * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * }
     */
    //用户唯一标识
    private String openid;
    //用户微信昵称
    private String nickname;
    //性别
    private String sex;
    //省份
    private String province;
    //城市
    private String city;
    //国家
    private String country;

    private String unionid;
}
