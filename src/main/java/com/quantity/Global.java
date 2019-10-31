package com.quantity;

import java.util.HashMap;
import java.util.Map;

import com.util.PropertiesLoader;
import com.util.StringUtils;

/**
 * CoolJava全局常量类
 * 
 * @author wangh
 * 
 */
public class Global
{
	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = new HashMap<String, String>();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("classpath:dbconfig.properties");
	
	/**
	 * 检查点层级：一级
	 */
	public final static String CHECK_LEVEL_ONE = "1";
	
	/**
	 * 检查点层级：二级
	 */
	public final static String CHECK_LEVEL_TWO = "2";
	
	/**
	 * 检查点层级：三级
	 */
	public final static String CHECK_LEVEL_THREE = "3";
	
	/**
	 * 检查点层级：四级
	 */
	public final static String CHECK_LEVEL_FOUR = "4";

	/**
	 * 是
	 */
	public static final String YES = "1";

	/**
	 * 否
	 */
	public static final String NO = "0";
	

	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
}
