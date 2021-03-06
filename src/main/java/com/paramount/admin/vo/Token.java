package com.paramount.admin.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 基于RestFul的方式登陆token
 */
public class Token  implements Serializable{
    private static final long serialVersionUID = -5796339785584122756L;

    private String token;

    /**
     * token到期时间
     */
    private Date expireTime;

    public Token(String token, Date expireTime) {
        this.token = token;
        this.expireTime = expireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
