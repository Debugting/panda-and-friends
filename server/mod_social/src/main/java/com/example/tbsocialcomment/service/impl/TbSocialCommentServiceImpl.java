package com.example.tbsocialcomment.service.impl;
import com.example.tbsocialcomment.dao.TbSocialCommentDao;
import com.example.tbsocialcomment.service.TbSocialCommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;


/**
 *用户评论 serverImpl
 */
@Service
@Transactional
public class TbSocialCommentServiceImpl   implements TbSocialCommentService {


	/**
	 * 注入dao
	 */
	@Resource
	private TbSocialCommentDao tbSocialCommentDao;
	/**
	 * 初始化
	 */
	@Override
	public TbSocialCommentDao initDao(){
		return tbSocialCommentDao;
	}


}
