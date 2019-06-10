package com.paramount.admin.vo;

import java.io.Serializable;

/**
 */
public class ResponseInfo implements Serializable{
    private static final long serialVersionUID = -1621001485330685349L;

    private String code;
    private String message;

    public ResponseInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
