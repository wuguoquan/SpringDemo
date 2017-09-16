package com.demo.domain;

import java.io.Serializable;

/**
 * Created by wuguoquan on 9/14/17.
 */
public class UserEx implements Serializable{

    // 私有字段
    private String loginname;
    private String password;
    private String username;

    // 公共构造器
    public UserEx() {
        super();
    }
    // set/get方法
    public String getLoginname() {
        return loginname;
    }
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

}
