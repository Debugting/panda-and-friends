package com.example.common.service;

import com.example.common.bean.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.common.dao.BaseDao;

import java.util.List;

/**
 * base业务层 使用jdk8新特性进行实现方法
 *
 * @param <T>
 * @param <D>
 */
public interface BaseService<T extends Page, D extends BaseDao<T>> {

    D initDao();

    /**
     * 根据主键删除
     */
    default int deleteByPrimaryKey(T t) {
        D baseDao = initDao();
        return baseDao.deleteByPrimaryKey(t);
    }

    /**
     * 新增
     */
    default int insert(T t) {
        D baseDao = initDao();
        return baseDao.insert(t);
    }

    /**
     * 新增
     */
    default int insertWithId(T t) {
        D baseDao = initDao();
        return baseDao.insertWithId(t);
    }

    /**
     * 更加主键查询
     */
    default T selectByPrimaryKey(T t) {
        D baseDao = initDao();
        return baseDao.selectByPrimaryKey(t);
    }

    /**
     * 按照条件查询
     */
    default List<T> selectByCondition(T t) {
        D baseDao = initDao();
        return baseDao.selectByCondition(t);
    }

    /**
     * 按照条件查询(调用selectByCondition),只取第一条记录
     */
    default T selectByConditionFirst(T t) {
        D baseDao = initDao();
        return baseDao.selectByConditionFirst(t);
    }

    /**
     * 分页查询
     */
    default T selectAllByPaging(T t) {
        D baseDao = initDao();
        PageHelper.startPage(t.getPage(), t.getPageSize());
        List<T> lists = baseDao.selectAll(t);
        PageInfo pageInfo = new PageInfo(lists);
        t.setRows(lists);
        t.setTotal((new Long(pageInfo.getTotal())).intValue());
        return t;
    }

    /**
     * 更新
     */
    default int update(T t) {
        BaseDao<T> baseDao = initDao();
        return baseDao.update(t);
    }

    /**
     * 查询所有,不分页
     */
    default List<T> selectAll(T t) {
        BaseDao<T> baseDao = initDao();
        return baseDao.selectAll(t);
    }

}