package com.jxust.client.test;

import com.jxust.api.model.HelloObject;

import com.jxust.api.service.HelloService;
import com.jxust.rpccore.model.RpcResponse;
import com.jxust.rpccore.proxy.RpcClientProxy;

public class TestClient {
    public static void main(String[] args) {
        RpcClientProxy proxy=new RpcClientProxy("127.0.0.1",9000);
        HelloService helloService=proxy.getProxy(HelloService.class);
        HelloObject helloObject=new HelloObject(12,"this is a message");
        String res=helloService.hello(helloObject);
        System.out.println(res);

    }
}
