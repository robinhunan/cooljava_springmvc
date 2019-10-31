package com.service.sys;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.sys.MenuMapper;
import com.model.sys.Menu;
import com.model.sys.MenuTree;
import com.model.sys.MenuTreeCheck;
import com.service.base.CrudService;

@Service
@Transactional
public class MenuService extends CrudService<MenuMapper, Menu>{
	
	@Resource
	public MenuMapper menuMapper;

	/**
	 * 获得顶级菜单
	 */
	public List<Menu> getMenuTop(Menu menu) {
		List<Menu> list = menuMapper.getAllListM(menu);
		return list;
	}
	
	/**
	 * 根据父节点递归获取菜单
	 */
	public List<Menu> getMenuByPidRec(Menu menu) {
		List<Menu> list = menuMapper.getAllListM(menu);
		digui(list, menu.getRoleId());
		return list;
	}
	
	@Override
	public Menu get(Menu menu) {
		return menuMapper.get(menu);
	}

	@Override
	public List<Menu> getAllList(Menu menu) {
		List<Menu> list = menuMapper.getAllListM(menu);
		digui(list, menu.getRoleId());
		return list;
	}

	/**
	 * 获取所有菜单(不递归)
	 * @param
	 * @return
	 */
	public List<Menu> getAllListN(Menu menu) {
		List<Menu> list = menuMapper.getAllList(menu);
		return list;
	}
	
	public void digui(List<Menu> list, int roleId){
		for(Menu m : list){
			m.setRoleId(roleId);
			List<Menu> listTemp = menuMapper.getByPidM(m);
			if(listTemp.size() > 0){
				m.setChildren(listTemp);
				digui(listTemp, roleId);
			}
		}
		
	}
	
	public void diguiTree(List<MenuTree> list){
		for(MenuTree m : list){
			List<MenuTree> listTemp = menuMapper.getByPidTree(m);
			if(listTemp.size() > 0){
				m.setChildren(listTemp);
				diguiTree(listTemp);
			}
		}
		
	}
	
	public void diguiTreeCheck(List<MenuTreeCheck> list, String roleId){
		for(MenuTreeCheck m : list){
			List<MenuTreeCheck> listTemp = menuMapper.getByPidTreeCheck(String.valueOf(m.getId()), roleId);
			if(listTemp.size() > 0){
				m.setChildren(listTemp);
				diguiTreeCheck(listTemp, roleId);
			}
		}
		
	}
	
	/**
	 * 获取所有菜单
	 * @param
	 * @return
	 */
	public List<MenuTree> getAllListJson(MenuTree menu) {
		List<MenuTree> list = menuMapper.getByPidTree(menu);
		diguiTree(list);
		return list;
	}
	
	/**
	 * 获取所有菜单
	 * @param
	 * @return
	 */
	public List<MenuTreeCheck> getAllListCheckJson(MenuTree menu, String roleId) {
		List<MenuTreeCheck> list = menuMapper.getByPidTreeCheck(String.valueOf(menu.getId()), roleId);
		diguiTreeCheck(list, roleId);
		return list;
	}

	@Override
	public void save(Menu menu) {
		if(menu.getId() == 0)
			menuMapper.insert(menu);
		else
			menuMapper.update(menu);
	}

	@Override
	public void delete(Integer id) {
		menuMapper.delete(id);
		diguiDel(id);
	}

	public void diguiDel(Integer id){
		Menu menu = new Menu();
		menu.setId(id);
		List<Menu> list = menuMapper.getByPid(menu);
		if(list.size() > 0){
			for(Menu m : list){
				menuMapper.delete(m.getId());
				diguiDel(m.getId());
			}
		}
	}
}
