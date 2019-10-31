package com.controller.sys;

import com.model.sys.Org;
import com.model.sys.OrgTree;
import com.model.user.User;
import com.service.sys.OrgService;
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

/**
 * @功能说明：部门机构
 */
@Controller
@RequestMapping("/org")
public class OrgController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrgService orgService;

	/**
	 * 部门机构列表跳转页面
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
		return "views/sys/orgList";
	}
	
	/**
	 * 分页获取部门机构
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/orgData")
	public Map<String, Object> orgData(HttpServletRequest request, Model model, Org org) throws UnsupportedEncodingException {
		 Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<Org> list = orgService.getListByPage(org);
	        Long count = orgService.getCount(org);
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
	 * 获取机构json数据(树)
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dataJson")
	public Map<String, Object> dataJson(HttpServletRequest request, Model model, Org org) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Org> list = orgService.getAllList(org);
        map.put("code", 0);
        map.put("msg", "ok");
        map.put("data", list);
        map.put("count", list.size());
		return map;
	}
	
	/**
	 * 获取机构，递归，用户编辑下拉选择树用
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/treeDataJson")
	public List<OrgTree> dataJson(HttpServletRequest request, Model model, OrgTree orgTree) {
		List<OrgTree> list = orgService.getTreeDataJson(orgTree);
		return list;
	}
	
	/**
	 * 删除部门机构
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, Model model, Org org) {
		String result = "1";
		try {
			orgService.delete(org.getId());
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 批量删除部门机构
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
				orgService.delete(Integer.parseInt(id));
			}
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 新增部门机构跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/form")
	public String form(Org org, HttpServletRequest request, Model model) {
		if(org.getPid() != null){
			Org temp = new Org();
			temp.setId(org.getPid());
			model.addAttribute("orgP",  orgService.get(temp));
		}
		return "views/sys/orgForm";
	}
	
	/**
	 * 修改部门机构跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(Org org, HttpServletRequest request, Model model) {
		org = orgService.get(org);
		model.addAttribute("org", org);
		return "views/sys/orgForm";
	}
	
	/**
	 * 保存部门机构
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, Model model, Org org) throws UnsupportedEncodingException {
		String result = "1";//结果标识 1：失败 0：成功
		try
		{
			//设置默认值
			orgService.save(org);
			result = "0";
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
	}
}
