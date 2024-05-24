package com.example.tbpapertype.service.impl;
import com.example.tbpapertype.dao.TbPaperTypeDao;
import com.example.tbpapertype.service.TbPaperTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *帖子类型 serverImpl
 */
@Service
@Transactional
public class TbPaperTypeServiceImpl   implements TbPaperTypeService {


	/**
	 * 注入dao
	 */
	@Resource
	private TbPaperTypeDao tbPaperTypeDao;
	/**
	 * 初始化
	 */
	@Override
	public TbPaperTypeDao initDao(){
		return tbPaperTypeDao;
	}


}
