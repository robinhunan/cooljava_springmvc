package com.service.${mouldName};

<#--import javax.transaction.Transactional;-->
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import com.mapper.${mouldName}.${className}Mapper;
import com.model.${mouldName}.${className};
import com.service.base.CrudService;

/**
 * @功能说明：${functionComment}
 * @作者： ${author}
 * @创建日期：${date}
 */
@Service
@Transactional
public class ${className}Service extends CrudService<${className}Mapper, ${className}> {
	
}