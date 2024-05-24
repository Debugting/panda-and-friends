package com.example.sysuser.service;


import com.example.common.service.BaseService;
import com.example.sysuser.bean.SysRole;
import com.example.sysuser.bean.SysUser;
import com.example.sysuser.dao.SysRoleDao;

import java.util.List;

/**
 * 后台角色 service
 */
public interface SysRoleService extends BaseService<SysRole, SysRoleDao> {

    int insert(SysRole sysRole, List<String> auths);

    int update(SysRole sysRole, List<String> auths);

    /**
     * 查询用户已有的权限
     */
    List<SysRole> queryByUser(SysUser sysUser);
}
