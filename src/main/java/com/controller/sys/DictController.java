package com.controller.sys;

import com.model.sys.Dict;
import com.model.user.User;
import com.service.sys.DictService;
import com.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dict")
public class DictController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DictService dictService;

	/**
	 * 数据字典列表跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model) {
		//获取当前用户
		//User user = (User) request.getSession().getAttribute("user");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
		user = (User) userService.get(user);
		model.addAttribute("user", user);
		return "views/sys/dictList";
	}
	
	
	/**
	 * 分页获取数据字典
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/dictData")
	public Map<String, Object> dictData(HttpServletRequest request, Model model, Dict dict) throws UnsupportedEncodingException {
		 Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Dict> list = dictService.getListByPage(dict);
	        Long count = dictService.getCount(dict);
	       
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
	 * 删除数据字典
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, Model model, Dict dict) {
		String result = "1";
		try {
			dictService.delete(dict.getId());
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 批量删除数据字典
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
				dictService.delete(Integer.parseInt(id));
			}
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 新增数据字典跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/form")
	public String form(Dict dict, HttpServletRequest request, Model model) {
		return "views/sys/dictAdd";
	}
	
	/**
	 * 新增数据字典跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(Dict dict, HttpServletRequest request, Model model) {
		dict = dictService.get(dict);
		model.addAttribute("dict", dict);
		return "views/sys/dictAdd";
	}
	
	/**
	 * 新增数据字典跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addV")
	public String addV(Dict dict, HttpServletRequest request, Model model) {
		dict = dictService.get(dict);
		Dict dic = new Dict();
		dic.setType(dict.getType());
		dic.setDescription(dict.getDescription());
		dic.setSort(dict.getSort() + 5);
		model.addAttribute("dict", dic);
		return "views/sys/dictAdd";
	}
	
	/**
	 * 保存数据字典
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, Model model, Dict dict) throws UnsupportedEncodingException {
		String result = "1";//结果标识 1：失败 0：成功
		try
		{
			//设置默认值
			dict.setStatus("0");//初始状态为可用
			dictService.save(dict);
			result = "0";
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 禁用/解禁
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/setUse")
	public String setUse(HttpServletRequest request, Model model, Dict dict) {
		String result = "1";
		dict = dictService.get(dict);
		try {
			if(dict.getStatus().equals("0")){
				dict.setStatus("1");
			}else{
				dict.setStatus("0");
			}
			dictService.save(dict);
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
