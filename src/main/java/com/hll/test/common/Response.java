package com.hll.test.common;/**
 * Create by shengqi on 2019/1/14
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Response {

    public static final Response SUCCESS=new Response(Code.SUCCESS);
    public static final Response NOT_LOGIN=new Response(Code.NOT_LOGIN);
    public static final Response LACK_OF_PARAM=new Response(Code.LACK_OF_PARAM);
    public static final Response PWD_ERROR=new Response(Code.PWD_ERROR);
    public static final Response DONOT_SIGN=new Response(Code.LACK_OF_PARAM);
    public static final Response NO_SIGN=new Response(Code.NO_SIGN);
    public static final Response NO_RESULT=new Response(Code.NO_RESULT);
    public static final Response VERCODE_ERROR=new Response(Code.VERCODE_ERROR);
    public static final Response NO_PERMISSION=new Response(Code.NO_PERMISSION);

    private static Logger logger= LoggerFactory.getLogger(Response.class);

    private Object data;
    private int code;
    private String desc;

    public Response() {
    }
    
    public Response(Object data) {
        this.data = data;
    }

    public Response(ICode code) {
        this.code = code.getCode();
        this.desc = code.getDesc();
    }

    public Response(int code){
        this.code=code;
    }

    public Response(Object object, ICode code) {
        this.data = object;
        this.code = code.getCode();
        this.desc = code.getDesc();
    }

    public static Response err(String errMsg) {
        Response response = new Response();
        response.setDesc(errMsg);
        response.setCode(900);
        logger.error(errMsg);
        return response;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
