package com.example.tbsocialpraised.service.impl;
import com.example.tbsocialpraised.dao.TbSocialPraisedDao;
import com.example.tbsocialpraised.service.TbSocialPraisedService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *点赞记录 serverImpl
 */
@Service
@Transactional
public class TbSocialPraisedServiceImpl   implements TbSocialPraisedService {


	/**
	 * 注入dao
	 */
	@Resource
	private TbSocialPraisedDao tbSocialPraisedDao;
	/**
	 * 初始化
	 */
	@Override
	public TbSocialPraisedDao initDao(){
		return tbSocialPraisedDao;
	}


}
