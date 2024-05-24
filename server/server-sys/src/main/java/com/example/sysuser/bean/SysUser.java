package com.example.sysuser.bean;

import com.example.common.bean.Page;

/**
 * 后台管理员 bean
 */
public class SysUser extends Page {

    /**
     * id
     */
    private Integer id;
    /**
     * 账号
     */
    private String act;
    /**
     * 密码
     */
    private String pwd;
    /**
     * 姓名
     */
    private String name;
    /**
     * 头像
     */
    private String icon;
    /**
     * 手机
     */
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
