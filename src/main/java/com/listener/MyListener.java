package com.listener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

import com.model.tool.Quartz;
import com.service.tool.QuartzService;
import com.util.QuartzUtils;

@Component
public class MyListener  implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger log = Logger.getLogger(MyListener.class);
	
	//定时器服务层
	@Autowired
	private QuartzService quartzService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent cre) {
			log.info(".......................初始化任务调度.......................");
			ServletContext servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
			Quartz qua = new Quartz();
			qua.setState("0");//运行中的任务
			List<Quartz> list = new ArrayList<Quartz>();
			try {
				list = quartzService.getAllList(qua);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//逐个启动各个任务
			for (Quartz quartz : list) {
				try {
					QuartzUtils.schedStart(quartz, servletContext);
				} catch (SchedulerException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
	}

}
