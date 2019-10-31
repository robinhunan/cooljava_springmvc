package com.model.sys;

import javax.persistence.Column;
import javax.persistence.Id;
import com.model.page.PageDto;

/**
 * @功能说明：部门机构
 */
public class Org extends PageDto implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	//字段
	private int id;//id
	private Integer pid;//父级id
	private String name;//机构名称
	private Long sort;//排序
	private String type;//机构类型,0:公司 1：部门
	
	//构造方法
	public Org() {
	}
	
	//get和set方法
	
	@Id @Column( name = "id"  ,nullable = false  , length = 10  )
	public int getId() {
		return  id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	@Column( name = "pid"  ,nullable = false  , length = 10  )
	public Integer getPid() {
		return  pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	
	@Column( name = "name"  ,nullable = false  , length = 100  )
	public String getName() {
		return  name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Column( name = "sort"  ,nullable = false  , precision = 10, scale = 0  )
	public Long getSort() {
		return  sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	
	@Column( name = "type"  ,nullable = false  , length = 1  )
	public String getType() {
		return  type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
