package com.controller.sys;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页Controller
 * @author 
 *
 */
@Controller
@RequestMapping("/index")
public class IndexController {

	private static Logger log = Logger.getLogger(IndexController.class);

	/**
	 * 后台主页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request, Model model) {
		log.info("转到首页！");
		return "views/sys/index";
	}
	
	/**
	 * 后台主页面（默认页）
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("main")
	public String main(HttpServletRequest request, Model model) {
		return "views/sys/main";
	}
	

	
}
