package com.model.sys;

import java.util.List;

public class OrgTree {
	int id;
	int pid;
	String name;
	int sort;
	private String type; //菜单类型：0：目录菜单 1：权限菜单
	private String open;
	List<OrgTree> children;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public List<OrgTree> getChildren() {
		return children;
	}
	public void setChildren(List<OrgTree> children) {
		this.children = children;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getOpen()
	{
		String fla = "false";
		if(this.pid == -1){
			fla = "true";
		}
		return fla;
	}
	public void setOpen(String open)
	{
		this.open = open;
	}
	
}
