package com.example.tbsocialvisited.service.impl;
import com.example.tbsocialvisited.dao.TbSocialVisitedDao;
import com.example.tbsocialvisited.service.TbSocialVisitedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *浏览记录 serverImpl
 */
@Service
@Transactional
public class TbSocialVisitedServiceImpl   implements TbSocialVisitedService {


	/**
	 * 注入dao
	 */
	@Resource
	private TbSocialVisitedDao tbSocialVisitedDao;
	/**
	 * 初始化
	 */
	@Override
	public TbSocialVisitedDao initDao(){
		return tbSocialVisitedDao;
	}


}
