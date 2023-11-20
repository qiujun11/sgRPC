package com.jxust.server.service.impl;

import com.jxust.api.model.HellowObject;
import com.jxust.api.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServiceImpl implements HelloService {
    private static final Logger logger= LoggerFactory.getLogger(HelloService.class);
    @Override
    public String hello(HellowObject hellowObject) {
        logger.info("接收到：{}",hellowObject.getMessage());
        return "这是调用的返回值，id="+hellowObject.getId();
    }
}
