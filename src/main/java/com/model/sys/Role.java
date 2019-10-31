package com.model.sys;

import com.model.page.PageDto;

public class Role extends PageDto{
	String name;
	String useable;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUseable() {
		return useable;
	}
	public void setUseable(String useable) {
		this.useable = useable;
	}
	
}
