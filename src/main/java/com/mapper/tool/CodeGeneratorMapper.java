package com.mapper.tool;

import java.util.List;
import java.util.Map;

/**
 * 说明： 代码生成器
 * @author 
 *
 */
public interface CodeGeneratorMapper {
	
	/**
	 * 获取所有的表名称
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getTablesList(Map<String, Object> params);
	
	/**
	 * 获取列数据
	 * 
	 * @param
	 * @return
	 */
	public List<Map> getListMap(Map<String, Object> params);
}
