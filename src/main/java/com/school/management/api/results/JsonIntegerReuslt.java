package com.school.management.api.results;

public class JsonIntegerReuslt {
    private String code;
    private String message;
    private int data;

    public JsonIntegerReuslt() {
        this.setCode(ResultCode.SUCCESS);
        this.setMessage("成功！");

    }

    public JsonIntegerReuslt(ResultCode code) {
        this.setCode(code);
        this.setMessage(code.msg());
    }

    public JsonIntegerReuslt(ResultCode code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public JsonIntegerReuslt(ResultCode code, String message,int data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }

    public String getCode() {
        return code;
    }
    public void setCode(ResultCode code) {
        this.code = code.val();
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
