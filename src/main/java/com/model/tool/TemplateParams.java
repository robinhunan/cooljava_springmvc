package com.model.tool;

import com.model.page.PageDto;

/**
 * 
 * @功能说明：代码生成数据模型
 */
public class TemplateParams extends PageDto {
	private static final long serialVersionUID = 1L;
	private String tableName;// 表名称
	private String functionComment;// 功能说明
	private String className;// 类名称
	private String classPath;// 生成文件的存放路径
	private String filePath;// 文件的存放路径,app_areacodeInf_AreacodeInf
	private String pkColumn;// 主键字段
	private String sortColumn;// 排序字段
	private String action;// action地址
	private String add;// 新增方法
	private String update;// 修改方法
	private String select;// 查询方法
	private String delete;// 删除方法
	private String addmenu;// 是否自动配置菜单
	private String pid;//上一级菜单
	private String author;//作者
	private String pattern;//代码模式
	private String mouldName;//模块名
	
	
	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getAddmenu() {
		return addmenu;
	}

	public void setAddmenu(String addmenu) {
		this.addmenu = addmenu;
	}

	public String getPkColumn() {
		return pkColumn;
	}

	public void setPkColumn(String pkColumn) {
		this.pkColumn = pkColumn;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFunctionComment() {
		return functionComment;
	}

	public void setFunctionComment(String functionComment) {
		this.functionComment = functionComment;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public String getMouldName() {
		return mouldName;
	}

	public void setMouldName(String mouldName) {
		this.mouldName = mouldName;
	}
	
}