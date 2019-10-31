package com.controller.${mouldName};

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.${mouldName}.${className};
import com.model.user.User;
import com.service.${mouldName}.${className}Service;
import com.service.user.UserService;

/**
 * @功能说明：${functionComment}
 * @作者： ${author}
 * @创建日期：${date}
 */
@Controller
@RequestMapping("/${classNameToL}")
public class ${className}Controller {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ${className}Service ${classNameToL}Service;

	/**
	 * ${functionComment}列表跳转页面
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
		return "views/${mouldName}/${classNameToL}List";
	}
	
	
	/**
	 * 分页获取${functionComment}
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/${classNameToL}Data")
	public Map<String, Object> ${classNameToL}Data(HttpServletRequest request, Model model, ${className} ${classNameToL}) throws UnsupportedEncodingException {
		 Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<${className}> list = ${classNameToL}Service.getListByPage(${classNameToL});
	        Long count = ${classNameToL}Service.getCount(${classNameToL});
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
	 * 删除${functionComment}
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, Model model, ${className} ${classNameToL}) {
		String result = "1";
		try {
			${classNameToL}Service.delete(${classNameToL}.getId());
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 批量删除${functionComment}
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
				${classNameToL}Service.delete(Integer.parseInt(id));
			}
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 新增${functionComment}跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/form")
	public String form(${className} ${classNameToL}, HttpServletRequest request, Model model) {
		return "views/${mouldName}/${classNameToL}Form";
	}
	
	/**
	 * 新增${functionComment}跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(${className} ${classNameToL}, HttpServletRequest request, Model model) {
		${classNameToL} = ${classNameToL}Service.get(${classNameToL});
		model.addAttribute("${classNameToL}", ${classNameToL});
		return "views/${mouldName}/${classNameToL}Form";
	}
	
	/**
	 * 保存${functionComment}
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, Model model, ${className} ${classNameToL}) throws UnsupportedEncodingException {
		String result = "1";//结果标识 1：失败 0：成功
		try{
			//设置默认值
			${classNameToL}Service.save(${classNameToL});
			result = "0";
		} catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
