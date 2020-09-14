package com.yellow.blog.common.lang;

import lombok.Data;

import java.io.Serializable;
@Data
public class Result implements Serializable {
    /**
     * 200正常数据 其他均为异常数据
     */
    private int code;
    private String msg;
    private Object data;


    private static Result getResult(int code, String msg, Object data) {
        Result result = new Result();
        result.setMsg(msg);
        result.setData(data);
        result.setCode(code);
        return result;
    }

    public static Result succ(int code,String msg,Object data){
        return getResult(code, msg, data);
    }



    public static Result succ(Object data){
        return succ(200,"操作成功",data);
    }

    public static Result fail(int code,String msg,Object data){
        return getResult(code, msg, data);
    }

    public static Result fail(String msg,Object data){
        return getResult(400, msg, data);
    }
    public static Result fail(String msg){
        return getResult(400, msg, null);
    }
}

