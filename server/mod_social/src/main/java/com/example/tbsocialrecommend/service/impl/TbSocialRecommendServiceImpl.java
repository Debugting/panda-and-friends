package com.example.tbsocialrecommend.service.impl;
import com.example.tbsocialrecommend.dao.TbSocialRecommendDao;
import com.example.tbsocialrecommend.service.TbSocialRecommendService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *推荐数据 serverImpl
 */
@Service
@Transactional
public class TbSocialRecommendServiceImpl   implements TbSocialRecommendService {


	/**
	 * 注入dao
	 */
	@Resource
	private TbSocialRecommendDao tbSocialRecommendDao;
	/**
	 * 初始化
	 */
	@Override
	public TbSocialRecommendDao initDao(){
		return tbSocialRecommendDao;
	}


}
