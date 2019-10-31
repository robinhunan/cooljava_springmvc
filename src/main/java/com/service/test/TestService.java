package com.service.test;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;

import com.mapper.test.TestMapper;
import com.model.test.Test;
import com.service.base.CrudService;

/**
 * @功能说明：test
 * @作者： yubing.zhu
 * @创建日期：2019-10-31
 */
@Service
@Transactional
public class TestService extends CrudService<TestMapper, Test> {
	
}