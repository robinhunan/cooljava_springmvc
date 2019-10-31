package com.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 */
public class Test2Quartz extends BaseQuartz implements Job  {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("CoolJava测试调度2 - 正在运行......");
	}

}
