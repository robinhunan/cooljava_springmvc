
package com.model.base;

import java.io.Serializable;

/**
 * 后台管理系统业务返回Message通用类
 */


public class AjaxResult implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7909728957176314157L;
	
	//返回code
	private Object code;
	//返回消息1
	private Object msg;
	//返回数据
	private Object data;
	//返回数据条数
	private Object count;
	
	
	public Object getCode() {
		return code;
	}
	public void setCode(Object code) {
		this.code = code;
	}
	public Object getMsg() {
		return msg;
	}
	public void setMsg(Object msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Object getCount() {
		return count;
	}
	public void setCount(Object count) {
		this.count = count;
	}

	
}