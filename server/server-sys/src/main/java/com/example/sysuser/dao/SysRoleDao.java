package com.example.sysuser.dao;

import com.example.common.dao.BaseDao;
import com.example.sysuser.bean.SysRole;
import com.example.sysuser.bean.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 后台角色 dao
 */
@Mapper
public interface SysRoleDao extends BaseDao<SysRole> {
    /**
     * 删除权限
     * @param sysRole
     */
    void deletAuths(SysRole sysRole);


    /**
     * 查询用户已有的权限
     */
    List<SysRole> queryByUser(SysUser sysUser);
}
