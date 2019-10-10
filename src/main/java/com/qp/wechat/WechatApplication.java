package com.qp.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class WechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatApplication.class, args);
    }

}
