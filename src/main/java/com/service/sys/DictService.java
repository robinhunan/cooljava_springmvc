package com.service.sys;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.sys.DictMapper;
import com.model.sys.Dict;
import com.service.base.CrudService;

@Service
@Transactional
public class DictService extends CrudService<DictMapper, Dict> {
	
}