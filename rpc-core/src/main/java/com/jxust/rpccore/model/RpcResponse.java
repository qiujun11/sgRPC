package com.jxust.rpccore.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class RpcResponse <T> implements Serializable {

    private Integer statusCode;

    private String message;

    private T data;

    public static <T> RpcResponse<T> success(T data){
        RpcResponse<T> rpcResponse=new RpcResponse<>();
        rpcResponse.setStatusCode(ResponseCode.SUCCESS);
        rpcResponse.setData(data);
        return rpcResponse;
    }

    public static <T> RpcResponse<T> fail(T data){
        RpcResponse<T> rpcResponse=new RpcResponse<>();
        rpcResponse.setStatusCode(ResponseCode.FAIL);
        rpcResponse.setData(data);
        return rpcResponse;
    }
}
