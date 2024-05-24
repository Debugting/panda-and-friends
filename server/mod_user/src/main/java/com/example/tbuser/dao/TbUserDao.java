package com.example.tbuser.dao;
import com.example.common.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.example.tbuser.bean.TbUser;

/**
 *用户 dao
 */
@Mapper
public interface TbUserDao  extends BaseDao<TbUser>{


}
