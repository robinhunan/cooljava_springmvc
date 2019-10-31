package com.service.sys;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.sys.RoleMapper;
import com.model.sys.Role;
import com.service.base.CrudService;

@Service
@Transactional
public class RoleService extends CrudService<RoleMapper, Role>{
	
	@Resource
	public RoleMapper roleMapper;

	@Transactional
	public void savePermission(String roleId, String ids) {
		String[] strs = ids.split(",");
		//删除已有关联关系
		roleMapper.deleteRM(Integer.parseInt(roleId));
		
		for(int i = 0; i < strs.length; i ++){
			roleMapper.insertRM(roleId, strs[i]);
			
		}
		//保存新的角色和菜单的管理关系
	}
}
