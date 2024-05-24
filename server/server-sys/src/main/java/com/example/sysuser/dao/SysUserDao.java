package com.example.sysuser.dao;

import com.example.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.example.sysuser.bean.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台管理员 dao
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUser> {
    /**
     * 批量新增
     */
    void insertBatch(@Param("userId") Integer userId, @Param("roles") List<String> roles);

    void deleteRoles(SysUser sysUser);

    /**
     * 修改密码
     */
    int updatePassword(SysUser user);
}
