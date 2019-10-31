package com.service.sys;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.sys.OrgMapper;
import com.model.sys.Org;
import com.model.sys.OrgTree;
import com.service.base.CrudService;

/**
 * @功能说明：部门机构
 */
@Service
@Transactional
public class OrgService extends CrudService<OrgMapper, Org> {

	@Resource
	public OrgMapper orgMapper;
	
	public List<OrgTree> getAllListJson(OrgTree org)
	{
		List<OrgTree> list = orgMapper.getByPidTree(org);
		diguiTree(list);
		return list;
	}
	
	/**
	 * 递归方法，根据父节点递归查询所有子节点
	 * @param list
	 */
	public void diguiTree(List<OrgTree> list){
		for(OrgTree m : list){
			List<OrgTree> listTemp = orgMapper.getByPidTree(m);
			if(listTemp.size() > 0){
				m.setChildren(listTemp);
				diguiTree(listTemp);
			}
		}
		
	}
	
	@Override
	public void delete(Integer id) {
		orgMapper.delete(id);
		diguiDel(id);
	}
	
	/**
	 * 递归删除
	 * @param id
	 */
	public void diguiDel(Integer id){
		Org org = new Org();
		org.setId(id);
		List<Org> list = orgMapper.getByPid(org);
		if(list.size() > 0){
			for(Org m : list){
				orgMapper.delete(m.getId());
				diguiDel(m.getId());
			}
			
		}
		
	}

	/**
	 * 获取机构，递归，用户编辑下拉选择树用
	 * @param orgTree
	 * @return
	 */
	public List<OrgTree> getTreeDataJson(OrgTree orgTree)
	{
		List<OrgTree> list = orgMapper.getByPidTree(orgTree);
		diguiTree(list);
		return list;
	}
}