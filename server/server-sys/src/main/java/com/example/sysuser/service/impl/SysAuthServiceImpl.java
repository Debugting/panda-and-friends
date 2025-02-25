package com.example.sysuser.service.impl;

import javax.annotation.Resource;

import com.example.sysuser.bean.SysAuth;
import com.example.sysuser.bean.SysRole;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.sysuser.service.SysAuthService;
import com.example.sysuser.dao.SysAuthDao;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SysAuthServiceImpl implements SysAuthService {

    @Resource
    private SysAuthDao sysAuthDao;

    @Override
    public SysAuthDao initDao() {
        return sysAuthDao;
    }

    @Override
    public List<SysAuth> queryByRole(SysRole sysRole) {
        return sysAuthDao.queryByRole(sysRole);
    }

    @Override
    public List<SysAuth> queryByUser(Integer userId) {
        return sysAuthDao.queryByUser(userId);
    }

    @Override
    public int deleteByPrimaryKey(SysAuth sysAuth) {
        sysAuthDao.deleteByPrimaryKey(sysAuth);

        //清理所有有父id,但是不存在的记录
        sysAuthDao.deleteUnlinked();
        return 0;
    }
}
