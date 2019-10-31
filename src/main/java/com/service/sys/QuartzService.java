package com.service.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.sys.DictMapper;

/**
 * 任务调度专业服务层，如果定时器中需要调用系统中的业务功能，可以写一个方法，供定时器类调用
 * 后续如果增加了定时任务，只需往此类中添加方法即可
 */
@Service
@Transactional
public class QuartzService {
	
	@Autowired
	public DictMapper dictMapper;
	
	public void testQuartz(){
		//以下为测试数据，演示用，具体的定时任务要执行的业务功能需要自己编写。
		
	}
	
	
}