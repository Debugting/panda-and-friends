package com.example.sysuser.bean;

import com.example.common.bean.Page;
import org.springframework.util.StringUtils;


/**
 * 后台角色 bean
 */
public class SysRole extends Page {

    /**
     * 后台角色
     */
    private Integer id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * {"name":"状态","1":"启用","0":"禁用"}
     */
    private Integer status;

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatus_() {
        if (StringUtils.isEmpty(status)) {
            return "";
        } else if (status.equals(1)) {
            return "启用";
        } else if (status.equals(0)) {
            return "禁用";
        }
        return "";
    }

}
