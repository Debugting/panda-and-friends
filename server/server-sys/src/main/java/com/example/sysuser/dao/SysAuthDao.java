package com.example.sysuser.dao;

import com.example.sysuser.bean.SysAuth;
import com.example.common.dao.BaseDao;
import com.example.sysuser.bean.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAuthDao extends BaseDao<SysAuth> {

    /**
     * 批量新增
     */
    void insertBatch(@Param("auths") List<String> auths, @Param("roleId") Integer roleId);

    /**
     * 查询角色下的权限
     */
    List<SysAuth> queryByRole(SysRole sysRole);

    /**
     * 查询用户权限
     */
    List<SysAuth> queryByUser(Integer userId);

    void deleteUnlinked();
}