package com.example.tbsocialcollected.service.impl;
import com.example.tbsocialcollected.dao.TbSocialCollectedDao;
import com.example.tbsocialcollected.service.TbSocialCollectedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *收藏记录 serverImpl
 */
@Service
@Transactional
public class TbSocialCollectedServiceImpl   implements TbSocialCollectedService {


	/**
	 * 注入dao
	 */
	@Resource
	private TbSocialCollectedDao tbSocialCollectedDao;
	/**
	 * 初始化
	 */
	@Override
	public TbSocialCollectedDao initDao(){
		return tbSocialCollectedDao;
	}


}
