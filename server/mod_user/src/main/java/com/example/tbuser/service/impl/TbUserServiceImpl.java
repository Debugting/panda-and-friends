package com.example.tbuser.service.impl;
import com.example.tbuser.dao.TbUserDao;
import com.example.tbuser.service.TbUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *用户 serverImpl
 */
@Service
@Transactional
public class TbUserServiceImpl   implements TbUserService {


	/**
	 * 注入dao
	 */
	@Resource
	private TbUserDao tbUserDao;
	/**
	 * 初始化
	 */
	@Override
	public TbUserDao initDao(){
		return tbUserDao;
	}


}
