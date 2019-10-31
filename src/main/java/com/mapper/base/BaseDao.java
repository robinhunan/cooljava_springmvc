/**
 */
package com.mapper.base;

import java.util.List;

/**
 * DAO支持类实现
 * @author 
 * @param <T>
 */
public interface BaseDao<T> {
	
	
	 /**
     * 总数
     * @param
     * @return
     */
    Long getCount(T entity);

    /**
     * 获取单条数据
     * @param
     * @return
     */
    T get(T entity);
    
    /**
     * 分页查询
     * @param
     * @return
     */
    List<T> getListByPage(T entity);
	
    /**
	 * 获取所有
	 * @param
	 * @return
	 */
	public List<T> getAllList(T entity);
	
	/**
	 * 新增
	 * @param
	 */
	public void insert(T entity);
	
	/**
	 * 修改
	 * @param
	 */
	public void update(T entity);
	
	/**
	 * 删除
	 * @param
	 */
	public void delete(Integer id);
	
}