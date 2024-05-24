package com.example.tbsocialbanner.service.impl;
import com.example.tbsocialbanner.dao.TbSocialBannerDao;
import com.example.tbsocialbanner.service.TbSocialBannerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *轮播图 serverImpl
 */
@Service
@Transactional
public class TbSocialBannerServiceImpl   implements TbSocialBannerService {


	/**
	 * 注入dao
	 */
	@Resource
	private TbSocialBannerDao tbSocialBannerDao;
	/**
	 * 初始化
	 */
	@Override
	public TbSocialBannerDao initDao(){
		return tbSocialBannerDao;
	}


}
