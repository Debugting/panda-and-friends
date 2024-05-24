package com.example.sysuser.service.impl;

import com.example.sysuser.bean.SysRole;
import com.example.sysuser.bean.SysUser;
import com.example.sysuser.dao.SysAuthDao;
import com.example.sysuser.dao.SysRoleDao;
import com.example.sysuser.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {

	@Resource
	private SysRoleDao sysRoleDao;
	@Resource
	private SysAuthDao sysAuthDao;


    @Override
    public SysRoleDao initDao() {
        return sysRoleDao;
    }

    @Override
    public int insert(SysRole sysRole, List<String> auths) {
        int insert = sysRoleDao.insert(sysRole);
        if(auths!=null && auths.size()>0){
            sysAuthDao.insertBatch(auths,sysRole.getId());
        }
        return insert;
    }

    @Override
    public int update(SysRole sysRole, List<String> auths) {
        Integer update = sysRoleDao.update(sysRole);
        sysRoleDao.deletAuths(sysRole);
        if(auths!=null && auths.size()>0) {
            sysAuthDao.insertBatch(auths, sysRole.getId());
        }
        return update;
    }

    @Override
    public List<SysRole> queryByUser(SysUser sysUser) {
        return sysRoleDao.queryByUser(sysUser);
    }
}
