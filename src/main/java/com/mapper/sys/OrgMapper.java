package com.mapper.sys;

import java.util.List;
import com.mapper.base.BaseDao;
import com.model.sys.Org;
import com.model.sys.OrgTree;

/**
 * @功能说明：部门机构
 */
public interface OrgMapper extends BaseDao<Org>{

	/**
	 * 获取菜单
	 * @param org
	 * @return
	 */
	public List<OrgTree> getByPidTree(OrgTree org);

	/**
	 * 根据父节点id获取机构
	 * @param org
	 * @return
	 */
	public List<Org> getByPid(Org org);
	
}
