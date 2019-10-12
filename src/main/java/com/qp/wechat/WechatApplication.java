package com.qp.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class WechatApplication {
    private final static Logger logger = LoggerFactory.getLogger(WechatApplication.class);
    public static void main(String[] args) {

        SpringApplication.run(WechatApplication.class, args);
        logger.info("程序启动完毕！");
    }

}
