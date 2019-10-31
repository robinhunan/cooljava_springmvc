package com.controller.sys;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.service.user.UserService;

import java.util.HashMap;
import java.util.Map;

/*
 * 登陆
 *
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	UserService userService;

	private static Logger log = Logger.getLogger(LoginController.class);

	/**
	 * 登录跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request, Model model) {
		log.info("进入到登录界面！");
		return "views/sys/login";
	}

	/**
	 * 登录
	 * @param request
	 * @param name
	 * @param pwd
	 * @return
	 */
	@ResponseBody
	@RequestMapping("login")
	public Map<String, Object>  login(HttpServletRequest request, String name, String pwd)
	{
		log.info("用户名" + name);
		log.info("密码" + pwd);
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
			subject.login(token);
			map.put("code", 0);
			map.put("msg", "登录成功");
		}
		catch (UnknownAccountException e)
		{
			map.put("code", 1);
			map.put("msg", "账号不存在");
		}
		catch (IncorrectCredentialsException e)
		{
			map.put("code", 1);
			map.put("msg", "账号或密码错误");
		}
		catch (LockedAccountException e)
		{
			map.put("code", 1);
			map.put("msg", "账号被禁用");
		}
		catch (AuthenticationException e)
		{
			map.put("code", 1);
			map.put("msg", "账户验证失败");
		}
		return map;
	}


	/**
	 * 退出
	 * @param request
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "views/sys/login";
	}

}
