package com.controller.user;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.model.sys.Role;
import com.model.user.User;
import com.service.sys.RoleService;
import com.service.user.UserService;
import com.util.Tool;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	/**
	 * 修改密码跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pwd")
	public String pwd(HttpServletRequest request, Model model) {
		//获取当前用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		user = (User) userService.get(user);
		model.addAttribute("user", user);
		return "views/user/password";
	}
	
	/**
	 * 个人信息跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/personInfo")
	public String personInfo(HttpServletRequest request, Model model) {
		//获取当前用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		user = (User) userService.get(user);
		model.addAttribute("user", user);
		return "views/user/personInfo";
	}
	
	/**
	 * 保存个人信息
	 * @param request
	 * @param model
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/saveInfo", method = RequestMethod.POST)
	public String saveInfo(HttpServletRequest request, Model model, User user) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		user.setName(name);
		try {
			userService.update(user);
			//改变session中保存用户的照片信息
			Subject subject = SecurityUtils.getSubject();
	    	PrincipalCollection newPrincipalCollection = 
	    			new SimplePrincipalCollection(user, user.getImg());
	    	//subject.runAs(newPrincipalCollection);
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
		
	}
	
	/**
	 * 保存密码
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/savePwd", method = RequestMethod.POST)
	public String savePwd(HttpServletRequest request, Model model, String id,String pwd, String pwd1, String pwd2) throws UnsupportedEncodingException {
		//获取当前用户
		User user = new User();
		user.setId(Integer.parseInt(id));
		user = (User) userService.get(user);
		try {
			if(user.getPwd().equals(Tool.MD5(pwd))){
				if(pwd1.equals(pwd2)){
					user.setPwd(Tool.MD5(pwd1));
					userService.update(user);
					return "0";
				}else{
					return "1";
				}
			}else{
				return "2";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "-1";
		}
		
	}
	
	/**
	 * 用户管理跳转
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	@RequiresPermissions("user:list")
	public String list(HttpServletRequest request, Model model) {
		//获取当前用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		user = (User) userService.get(user);
		model.addAttribute("user", user);
		
		return "views/user/userList";
	}
	
	/**
	 * 用户数据
	 * @param request
	 * @param model
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/data")
	public Map<String, Object> data(HttpServletRequest request, Model model, User user) throws UnsupportedEncodingException {
		
		List<User> list = userService.getListByPage(user);
        Long count = userService.getCount(user);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", count);
        map.put("data", list);

        return map;
	}
	
	/**
	 * 新增用户
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/form")
	@RequiresPermissions("user:add")
	public String form(User user, HttpServletRequest request, Model model) {
		user = userService.get(user);
		model.addAttribute("u", user);
		//查询所有角色
		Role role = new Role();
		role.setUseable("0");
		List<Role> list = roleService.getAllList(role);
		model.addAttribute("roles", list);
		return "views/user/userForm";
	}
	
	/**
	 * 保存个人信息
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		User user = new User();
		//参数构造
		String id = request.getParameter("id");
		String loginName = request.getParameter("loginName");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String mobile = request.getParameter("mobile");
		String pwd = request.getParameter("pwd");
		String roleId = request.getParameter("roleId");
		String orgId = request.getParameter("orgId");
		
		if(id != "" && id != null){
			user.setId(Integer.parseInt(id));
		}
		if(pwd == "" || pwd == null){
			user.setPwd(Tool.MD5("123456"));
		}
		user.setLoginName(loginName);
		user.setName(name);
		user.setEmail(email);
		user.setMobile(mobile);
		user.setRoleId(Integer.parseInt(roleId));
		user.setOrgId(Integer.parseInt(orgId));
		try {
			userService.save(user);
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
		
	}
	
	/**
	 * 删除用户
	 * @param request
	 * @param model
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
//	@RequiresPermissions(value={"user:delete","user:update"},logical=Logical.OR)
	public String delete(HttpServletRequest request, Model model, User user) {
		String result = "1";
		try {
			userService.delete(user.getId());
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 禁用/解禁角色
	 * @param request
	 * @param model
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/setUse")
//	@RequiresPermissions("user:update")
	public String setUse(HttpServletRequest request, Model model, User user) {
		String result = "1";
		user = userService.get(user);
		try {
			if(user.getStatus().equals("0")){
				user.setStatus("1");
			}else{
				user.setStatus("0");
			}
			userService.save(user);
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
