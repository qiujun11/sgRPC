package com.jxust.rpccore.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RpcClient {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);

    public Object sentRequest(RpcRequest rpcRequest,String host,int port){

        try (Socket socket=new Socket(host,port)){
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream=new ObjectInputStream(socket.getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
