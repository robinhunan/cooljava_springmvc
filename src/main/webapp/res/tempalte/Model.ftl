package com.model.${mouldName};

import javax.persistence.Column;
import javax.persistence.Id;
import com.model.page.PageDto;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @功能说明：${functionComment}
 * @作者： ${author}
 * @创建日期：${date}
 */
public class ${className} extends PageDto implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
	//字段
	<#list cloums as c>
	private ${c.cloumsType} ${ c.columnName};//${ c.columnComment}
	</#list>
	
	//构造方法
	public ${className}() {
	}
	
	//get和set方法
	<#list cloums as c>
	
	${c.cloums_top}
	public ${c.cloumsType} get${ c.UpUmnName}() {
		return  ${ c.columnName};
	}

	public void set${ c.UpUmnName}(${c.cloumsType} ${ c.columnName}) {
		this.${ c.columnName} = ${ c.columnName};
	}

	</#list>
	
}
