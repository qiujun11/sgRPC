package com.jxust.server.test;

import com.jxust.api.service.HelloService;
import com.jxust.rpccore.model.RpcServer;
import com.jxust.rpccore.register.ServiceRegistry;
import com.jxust.rpccore.register.impl.DefaultServiceRegistry;
import com.jxust.server.service.impl.HelloServiceImpl;

public class TestServer {
    //    public static void main(String[] args) {
//        HelloService helloService=new HelloServiceImpl();
//        RpcServer rpcServer=new RpcServer();
//        rpcServer.register(helloService,9000);
//    }
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        ServiceRegistry serviceRegistry = new DefaultServiceRegistry();
        serviceRegistry.register(helloService);
        RpcServer rpcServer = new RpcServer(serviceRegistry);
        rpcServer.start(9000);

    }
}
