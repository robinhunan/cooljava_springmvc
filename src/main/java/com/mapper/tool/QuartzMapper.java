package com.mapper.tool;

import java.util.List;
import java.util.Map;

import com.mapper.base.BaseDao;
import com.model.tool.Quartz;

/**
 * @功能说明：调度管理
 */
public interface QuartzMapper extends BaseDao<Quartz>{
	
	 /**
	 * 获取所有
	 * @param entity
	 * @return
	 */
	public List<Map> getAllListByMap(Quartz entity);
	
}
