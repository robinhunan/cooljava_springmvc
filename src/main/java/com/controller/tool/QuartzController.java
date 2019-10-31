package com.controller.tool;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.base.AjaxResult;
import com.model.tool.Quartz;
import com.model.user.User;
import com.service.tool.QuartzService;
import com.service.user.UserService;
import com.util.QuartzUtils;

/**
 * @功能说明：调度管理
 */
@Controller
@RequestMapping("/quartz")
public class QuartzController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private QuartzService quartzService;

	/**
	 * 调度管理列表跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model) {
		//获取当前用户
		User user = (User) request.getSession().getAttribute("user");
		user = (User) userService.get(user);
		model.addAttribute("user", user);
		return "views/tool/quartzList";
	}
	
	
	/**
	 * 分页获取调度管理
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/quartzData")
	public Map<String, Object> quartzData(HttpServletRequest request, Model model, Quartz quartz) throws UnsupportedEncodingException {
		 Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Quartz> list = quartzService.getListByPage(quartz);
	        Long count = quartzService.getCount(quartz);
	        map.put("code", 0);
	        map.put("msg", "");
	        map.put("count", count);
	        map.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return map;
	}
	
	/**
	 * 删除调度管理
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, Model model, Quartz quartz) {
		String result = "1";
		quartz = quartzService.get(quartz);
		try {
			quartzService.delete(quartz.getId());
			QuartzUtils.delScheduleJob(quartz.getJobName(), quartz.getJobGroup());// 删除任务
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 批量删除调度管理
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteBatch")
	public String deleteBatch(HttpServletRequest request, Model model, String ids) {
		String result = "1";
		try {
			String[] idarr = ids.split(",");
			for(String id : idarr){
				Quartz entity = new Quartz();
				entity.setId(Integer.parseInt(id));
				entity = quartzService.get(entity);
				quartzService.delete(Integer.parseInt(id));
				QuartzUtils.delScheduleJob(entity.getJobName(), entity.getJobGroup());// 删除任务
			}
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 新增调度管理跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/form")
	public String form(Quartz quartz, HttpServletRequest request, Model model) {
		return "views/tool/quartzForm";
	}
	
	/**
	 * 新增调度管理跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(Quartz quartz, HttpServletRequest request, Model model) {
		quartz = quartzService.get(quartz);
		model.addAttribute("quartz", quartz);
		return "views/tool/quartzForm";
	}
	
	/**
	 * 保存调度管理
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public AjaxResult save(HttpServletRequest request, Model model, Quartz quartz) throws UnsupportedEncodingException {
		AjaxResult result = new AjaxResult();
		result.setCode("0");
		ServletContext context=request.getSession().getServletContext();
		try
		{
			if(quartz.getId() == 0){
				List<Quartz> list = quartzService.getAllList(quartz);
				if(list.size() > 0 ){
					result.setCode("2");
					result.setMsg("定时器名称不能重复");
				}else{
					//设置默认值
					quartz.setState("0");//初始状态为可用
					result = quartzService.saveT(quartz, context);
				}
			}else{
				//设置默认值
				quartzService.save(quartz);
				result.setCode("0");
				result.setMsg("保存成功");
				// 重启调度器
				QuartzUtils.rescheduleJob(quartz, context);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 禁用/解禁定时任务
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/setUse")
	public String setUse(HttpServletRequest request, Model model, Quartz quartz) {
		String result = "1";
		quartz = quartzService.get(quartz);
		ServletContext context=request.getSession().getServletContext();
		try {
			if(quartz.getState().equals("0")){
				quartz.setState("1");
			}else{
				quartz.setState("0");
			}
			quartzService.save(quartz);
			//调度的处理
			if (quartz.getState().equals("0")) {// 运行中
				QuartzUtils.rescheduleJob(quartz, context);
			} else if (quartz.getState().equals("1")) {// 暂停
				QuartzUtils.pauseJob(quartz.getJobName(), quartz.getJobGroup());
			}
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
