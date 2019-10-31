package com.service.tool;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mapper.tool.QuartzMapper;
import com.model.base.AjaxResult;
import com.model.tool.Quartz;
import com.service.base.CrudService;
import com.util.QuartzUtils;

/**
 * @功能说明：调度管理
 */
@Transactional
@Repository("QuartzService")
public class QuartzService extends CrudService<QuartzMapper, Quartz> {
	
	private static Logger log = Logger.getLogger(QuartzService.class);
	
	/**
	 * 获取所有
	 * @param entity
	 * @return
	 */
	public List<Map> getAllListByMap(Quartz entity) {
		return dao.getAllListByMap(entity);
	}
	
	/**
	 * 保存或者更新
	 * @param entity
	 */
	@Transactional
	public AjaxResult saveT(Quartz entity, ServletContext context) {
		AjaxResult result = new AjaxResult();
		result.setCode("0");
		result.setMsg("保存成功");
		if(entity.getId() == 0){
			// 创建调度任务
			try {
				QuartzUtils.schedStart(entity, context);
				dao.insert(entity);
			} catch (ClassNotFoundException e) {
				log.error("类路径错误！");
				result.setCode("3");
				result.setMsg("类路径错误！");
			} catch (SchedulerException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				log.error("cron表达式不正确！");
				result.setCode("4");
				result.setMsg("cron表达式不正确！");
			}
		}else{
			dao.update(entity);
		}
		return result;
	}
}