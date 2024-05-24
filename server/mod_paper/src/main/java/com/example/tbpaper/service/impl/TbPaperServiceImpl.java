package com.example.tbpaper.service.impl;
import com.example.tbpaper.dao.TbPaperDao;
import com.example.tbpaper.service.TbPaperService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *帖子 serverImpl
 */
@Service
@Transactional
public class TbPaperServiceImpl   implements TbPaperService {


	/**
	 * 注入dao
	 */
	@Resource
	private TbPaperDao tbPaperDao;
	/**
	 * 初始化
	 */
	@Override
	public TbPaperDao initDao(){
		return tbPaperDao;
	}


}
