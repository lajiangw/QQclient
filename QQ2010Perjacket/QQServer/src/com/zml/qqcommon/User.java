package com.zml.qqcommon;

import java.io.Serializable;

/**
 * @author 张民磊
 * @version 1.0
 * 表示客户的信息
 */


public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;  //用户名
    private String paswd;   //密码

    public User(String userId, String paswd) {
        this.userId = userId;
        this.paswd = paswd;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaswd() {
        return paswd;
    }

    public void setPaswd(String paswd) {
        this.paswd = paswd;
    }
}
