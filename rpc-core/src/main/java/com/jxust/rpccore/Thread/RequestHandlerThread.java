package com.jxust.rpccore.Thread;

import com.jxust.rpccore.handler.RequestHandler;
import com.jxust.rpccore.model.RpcRequest;
import com.jxust.rpccore.model.RpcResponse;
import com.jxust.rpccore.register.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class RequestHandlerThread implements Runnable{
    private  static final Logger logger= LoggerFactory.getLogger(RequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private ServiceRegistry serviceRegistry;

    public RequestHandlerThread(Socket socket, RequestHandler requestHandler, ServiceRegistry serviceRegistry) {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream())){
            RpcRequest rpcRequest=(RpcRequest) objectInputStream.readObject();
            String interfaceName=rpcRequest.getInterfaceName();
            Object service=serviceRegistry.getService(interfaceName);
            Object result=requestHandler.handle(rpcRequest,service);
            objectOutputStream.writeObject(RpcResponse.success(result));
            objectOutputStream.flush();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
