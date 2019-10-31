package com.model.tool;

import javax.persistence.Column;
import javax.persistence.Id;
import com.model.page.PageDto;

/**
 * @功能说明：调度管理
 */
public class Quartz extends PageDto implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	//字段
	private int id;//
	private String jobName;//定时器名称
	private String jobGroup;//所属组
	private String classPath;//类路径
	private String cronStr;//cron表达式
	private String state;//状态
	private String mark;//备注
	
	//构造方法
	public Quartz() {
	}
	
	//get和set方法
	
	@Id @Column( name = "ID" , unique = true  ,nullable = false  , length = 10  )
	public int getId() {
		return  id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	@Column( name = "JOB_NAME"  , length = 50  )
	public String getJobName() {
		return  jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	
	@Column( name = "JOB_GROUP"  , length = 50  )
	public String getJobGroup() {
		return  jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	
	@Column( name = "CLASS_PATH"  , length = 50  )
	public String getClassPath() {
		return  classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	
	@Column( name = "CRON_STR"  , length = 50  )
	public String getCronStr() {
		return  cronStr;
	}

	public void setCronStr(String cronStr) {
		this.cronStr = cronStr;
	}

	
	@Column( name = "STATE"  , length = 1  )
	public String getState() {
		return  state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	@Column( name = "MARK"  , length = 100  )
	public String getMark() {
		return  mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	
}
