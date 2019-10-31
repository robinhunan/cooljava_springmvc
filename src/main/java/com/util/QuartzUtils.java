package com.util;

import java.text.ParseException;

import javax.servlet.ServletContext;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.model.tool.Quartz;

public class QuartzUtils {

	// 创建调度任务
	public static void schedStart(Quartz quartz, ServletContext servletContext) throws SchedulerException, InterruptedException, ClassNotFoundException, ParseException {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = null;
		sched = sf.getScheduler();
		Class<Job> clazz = (Class<Job>) Class.forName(quartz.getClassPath());
		// 获取工作实例
		JobDetail job = JobBuilder.newJob(clazz).withIdentity(quartz.getJobName(), quartz.getJobGroup()).build();
		if (servletContext != null) {
			job.getJobDataMap().put("servletContext", servletContext);
			job.getJobDataMap().put("quartz", quartz);
		}
		// 获取 触发器实例
		Trigger trigger = getTrigger(quartz.getJobName(), quartz.getJobGroup(), quartz.getCronStr());
		// 判断触发器是否存在
		if (sched.checkExists(trigger.getKey())) {// 存在
			// 存在
			throw new ObjectAlreadyExistsException("触发器已存在");
		} else if (sched.checkExists(job.getKey())) {
			// 注册并进行调度
			trigger = getTrigger(quartz.getJobName(), quartz.getJobGroup(), quartz.getCronStr());
			sched.scheduleJob(trigger);
		} else {
			// 注册并进行调度
			sched.scheduleJob(job, trigger);
		}
		// 启动调度器
		if (!sched.isStarted()) {
			sched.start();
		}

	}

	// 创建触发器
	private static Trigger getTrigger(String JobName, String group, String cronStr) throws ParseException {
		Trigger trigger = null;
		TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withIdentity(JobName, group).withSchedule(CronScheduleBuilder.cronSchedule(cronStr));
		trigger = triggerBuilder.build();
		return trigger;
	}

	// 暂停调度器
	public static void pauseJob(String JobName, String group) throws SchedulerException {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler scheduler = sf.getScheduler();
		JobKey jobKey = JobKey.jobKey(JobName, group);
		scheduler.pauseJob(jobKey);
	}

	// 重启调度器
	public static void rescheduleJob(Quartz quartz, ServletContext servletContext) throws SchedulerException, InterruptedException, ClassNotFoundException, ParseException {
		delScheduleJob(quartz.getJobName(), quartz.getJobGroup());// 删除调度器
		schedStart(quartz, servletContext);// 启动调度

	}

	// 删除调度器
	public static void delScheduleJob(String JobName, String group) throws SchedulerException {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		JobKey jobKey = JobKey.jobKey(JobName, group);
		sched.deleteJob(jobKey);
	}
}
