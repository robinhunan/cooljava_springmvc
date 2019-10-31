package com.controller.web;

import java.io.UnsupportedEncodingException;
import java.util.Date;
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

import com.model.user.User;
import com.model.web.FriendLink;
import com.service.user.UserService;
import com.service.web.FriendLinkService;

@Controller
@RequestMapping("/friendLink")
public class FriendLinkController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private FriendLinkService friendLinkService;

	/**
	 * 友情链接列表跳转页面
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
		
		return "views/web/flinklist";
	}
	
	
	/**
	 * 分页获取友情链接
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/fLinkInfo")
	public Map<String, Object> fLinkInfo(HttpServletRequest request, Model model, FriendLink friendLink) throws UnsupportedEncodingException {
		friendLink.setWebname(friendLink.getWebname() == null?friendLink.getWebname():java.net.URLDecoder.decode(friendLink.getWebname(),"UTF-8"));
		List<FriendLink> list = friendLinkService.getListByPage(friendLink);
        Long count = friendLinkService.getCount(friendLink);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", count);
        map.put("data", list);

        return map;
		
	}
	
	/**
	 * 删除友情链接
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, Model model, FriendLink friendLink) {
		String result = "1";
		try {
			friendLinkService.delete(friendLink.getId());
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 批量删除友情链接
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
				friendLinkService.delete(Integer.parseInt(id));
			}
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 新增友情链接跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/form")
	public String form(FriendLink friendLink, HttpServletRequest request, Model model) {
		return "views/web/flinkadd";
	}
	
	/**
	 * 新增友情链接跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(FriendLink friendLink, HttpServletRequest request, Model model) {
		friendLink = friendLinkService.get(friendLink);
		model.addAttribute("friendLink", friendLink);
		return "views/web/flinkadd";
	}
	
	/**
	 * 保存友情链接
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, Model model, FriendLink friendLink) throws UnsupportedEncodingException {
		String result = "1";//结果标识 1：失败 0：成功
		try
		{
			//设置默认值
			friendLink.setDispos("首页");
			friendLink.setAddtime(new Date());
			friendLinkService.save(friendLink);
			result = "0";
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
	}
	
}
