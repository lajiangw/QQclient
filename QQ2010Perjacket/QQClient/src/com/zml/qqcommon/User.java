package com.zml.qqcommon;

import java.io.Serializable;

/**
 * @author 张民磊
 * @version 1.0
 * 表示客户的信息
 */


public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    public String userId;  //用户名
    public String paswd;   //密码

    public User(){}
    public User(String userId, String paswd) {
        this.userId = userId;
        this.paswd = paswd;
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
