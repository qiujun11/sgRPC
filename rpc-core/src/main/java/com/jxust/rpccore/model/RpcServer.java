package com.jxust.rpccore.model;

import com.jxust.rpccore.Thread.WorkerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class RpcServer {
    private final ExecutorService threadPool;
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    public RpcServer(){
        int corePoolSize =5;
        int maximumPoolSize=50;
        long keepAliveTime=60;
        BlockingQueue<Runnable> workingQueue=new ArrayBlockingQueue<>(100);
        ThreadFactory threadFactory=Executors.defaultThreadFactory();
        threadPool=new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,TimeUnit.SECONDS,workingQueue,threadFactory);

    }

    public void register(Object server,int port){
        try (ServerSocket serverSocket=new ServerSocket(port)){
            logger.info("服务器正在启动.....");
            Socket socket;
            while ((socket=serverSocket.accept())!=null){
                logger.info("客户端连接！IP为："+socket.getInetAddress());
                threadPool.execute(new WorkerThread(socket,server));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}