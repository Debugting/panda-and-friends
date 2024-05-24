package com.example.sysuser.service;

import com.example.sysuser.bean.SysAuth;
import com.example.sysuser.bean.SysRole;
import com.example.sysuser.dao.SysAuthDao;
import com.example.common.service.BaseService;

import java.util.List;

public interface SysAuthService extends BaseService<SysAuth, SysAuthDao> {

    /**
     * 查询角色下的权限
     */
    List<SysAuth> queryByRole(SysRole sysRole);

    /**
     * 查询用户权限
     */
    List<SysAuth> queryByUser(Integer userId);
}