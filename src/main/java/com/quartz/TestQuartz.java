package com.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import com.service.sys.QuartzService;
import com.util.SpringContextUtil;
/**
 * 测试Quartz
 */
public class TestQuartz extends BaseQuartz implements Job  {

	public static ApplicationContext appCtx = SpringContextUtil.getApplicationContext();
	
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		System.out.println("CoolJava测试调度1 - 调度正在运行......");
		//定时任务有时需要完成业务功能，以下提供了一个方法可以调用系统中方法的样例，可以把多个方法写在QuartzService这个类中
       // QuartzService quartzService = (QuartzService)appCtx.getBean("quartzService");
       // quartzService.testQuartz();
	}

}
