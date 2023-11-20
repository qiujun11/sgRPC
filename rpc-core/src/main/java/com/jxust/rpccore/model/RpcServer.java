package com.jxust.rpccore.model;

import com.jxust.rpccore.Thread.RequestHandlerThread;
import com.jxust.rpccore.handler.RequestHandler;
import com.jxust.rpccore.register.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class RpcServer {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;
    private final ExecutorService threadPool;
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private final ServiceRegistry serviceRegister;

    private RequestHandler requestHandler = new RequestHandler();

//    public RpcServer(){
//        int corePoolSize =5;
//        int maximumPoolSize=50;
//        long keepAliveTime=60;
//        BlockingQueue<Runnable> workingQueue=new ArrayBlockingQueue<>(100);
//        ThreadFactory threadFactory=Executors.defaultThreadFactory();
//        threadPool=new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,TimeUnit.SECONDS,workingQueue,threadFactory);
//
//    }

    //    public void register(Object server, int port){
//        try (ServerSocket serverSocket=new ServerSocket(port)){
//            logger.info("服务器正在启动.....");
//            Socket socket;
//            while ((socket=serverSocket.accept())!=null){
//                logger.info("客户端连接！IP为："+socket.getInetAddress());
//                threadPool.execute(new WorkerThread(socket,server));
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public RpcServer(ServiceRegistry serviceRegister) {
        this.serviceRegister = serviceRegister;
        BlockingQueue<Runnable> workingQueue=new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory=Executors.defaultThreadFactory();
        threadPool=new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_POOL_SIZE,KEEP_ALIVE_TIME,TimeUnit.SECONDS,workingQueue,threadFactory);
    }

    public void start(int port){
        try (ServerSocket serverSocket=new ServerSocket(port)){
            logger.info("服务器正在启动.....");
            Socket socket;
            while ((socket=serverSocket.accept())!=null){
                logger.info("客户端连接！{}:{}",socket.getInetAddress(),socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegister));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.info("服务器启动失败：{}",e.getMessage());
        }
    }


}
