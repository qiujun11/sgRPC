package com.jxust.rpccore.Thread;

import com.jxust.rpccore.model.RpcRequest;
import com.jxust.rpccore.model.RpcResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class WorkerThread implements Runnable{
    private Socket socket;

    private Object service;

    public WorkerThread(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        try (ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream())){
            RpcRequest rpcRequest=(RpcRequest) objectInputStream.readObject();
            Method method=service.getClass().getMethod(rpcRequest.getMethodName(),rpcRequest.getParamTypes());
            Object result=method.invoke(service,rpcRequest.getParameters());
            objectOutputStream.writeObject(RpcResponse.success(result));
            objectOutputStream.flush();

        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}
