package com.mapper.sys;

import org.apache.ibatis.annotations.Param;

import com.mapper.base.BaseDao;
import com.model.sys.Role;

public interface RoleMapper extends BaseDao<Role>{

	/**
	 * 删除角色和菜单管理关系
	 * @param id
	 */
	public void deleteRM(Integer id);
	
	/**
	 * 保存角色和菜单管理关系
	 * @param
	 */
	public void insertRM(@Param("roleId") String roleId, @Param("menuId") String menuId);
	
}
