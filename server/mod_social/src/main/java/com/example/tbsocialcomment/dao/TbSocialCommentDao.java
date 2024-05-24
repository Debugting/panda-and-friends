package com.example.tbsocialcomment.dao;
import com.example.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.example.tbsocialcomment.bean.TbSocialComment;

/**
 *用户评论 dao
 */
@Mapper
public interface TbSocialCommentDao  extends BaseDao<TbSocialComment>{


}
