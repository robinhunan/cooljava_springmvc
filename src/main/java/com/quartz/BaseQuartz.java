package com.quartz;

import javax.servlet.ServletContext;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.model.tool.Quartz;
import com.service.tool.QuartzService;
import com.util.QuartzUtils;

/**
 * 基础Quartz
 * 
 * @功能说明：
 */
public class BaseQuartz {
	
	@Autowired
	private QuartzService quartzService;
	
	/**
	 * 更改调度状态,且停止任务
	 */
	public void updateQuartzState(Quartz quartz, ServletContext servletContext) {
		ApplicationContext aContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		quartz.setState("1");
		quartzService.update(quartz);
		try {
			QuartzUtils.pauseJob(quartz.getJobName(), quartz.getJobGroup());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
