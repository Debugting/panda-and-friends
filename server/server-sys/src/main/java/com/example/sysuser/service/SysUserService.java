package com.example.sysuser.service;

import com.example.common.service.BaseService;
import com.example.sysuser.bean.SysUser;
import com.example.sysuser.dao.SysUserDao;

import java.util.List;

/**
 * 后台管理员 service
 */
public interface SysUserService extends BaseService<SysUser, SysUserDao> {

    int insert(SysUser sysUser, List<String> roles);

    int update(SysUser sysUser, List<String> roles);

    /**
     * 修改密码
     */
    int updatePassword(SysUser user);
}