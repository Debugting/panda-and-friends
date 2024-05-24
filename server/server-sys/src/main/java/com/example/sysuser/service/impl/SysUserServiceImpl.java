package com.example.sysuser.service.impl;

import com.example.sysuser.bean.SysUser;
import com.example.sysuser.dao.SysRoleDao;
import com.example.sysuser.dao.SysUserDao;
import com.example.sysuser.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * 后台管理员 serverImpl
 */
@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public SysUserDao initDao() {
        return sysUserDao;
    }

    @Override
    public int insert(SysUser sysUser, List<String> roles) {
        int insert = sysUserDao.insert(sysUser);
        if (roles.size() > 0) {
            sysUserDao.insertBatch(sysUser.getId(), roles);
        }
        return insert;
    }

    @Override
    public int update(SysUser sysUser, List<String> roles) {
        Integer update = sysUserDao.update(sysUser);
        sysUserDao.deleteRoles(sysUser);
        if (roles != null && roles.size() > 0) {
            sysUserDao.insertBatch(sysUser.getId(), roles);
        }
        return update;
    }

    @Override
    public int updatePassword(SysUser user) {
        return sysUserDao.updatePassword(user);
    }
}
