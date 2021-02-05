package com.library.network.bean;

/**
 * 错误信息实体
 */
public class ErrMsg {

    private int code;
    private String msg;

    public ErrMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}