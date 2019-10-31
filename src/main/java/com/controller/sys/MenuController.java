package com.controller.sys;

import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.model.base.AjaxResult;
import com.model.sys.Menu;
import com.model.sys.MenuTree;
import com.model.sys.MenuTreeCheck;
import com.model.user.User;
import com.service.sys.MenuService;
import com.service.user.UserService;

@Controller
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@Autowired
	private UserService userService;
	
	/*初始化菜单方法start*/
	
	/**
	 * 获取顶级菜单
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMenuTop")
	public List<Menu> getMenuTop(HttpServletRequest request, Model model, Menu menu) {
		 
		//获取当前用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		user = (User) userService.get(user);
		menu.setRoleId(user.getRoleId());
		//顶级菜单标志为1
		menu.setIsTop("1");
		
		List<Menu> list = menuService.getMenuTop(menu);
		return list;
	 }
	
	/**
	 * 根据父节点递归获取菜单
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMenuByPidRec")
	public List<Menu> getMenuByPidRec(HttpServletRequest request, Model model, Menu menu) {
		//获取当前用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		user = (User) userService.get(user);
		menu.setRoleId(user.getRoleId());
		menu.setPid(menu.getId());
		
		List<Menu> list = menuService.getMenuByPidRec(menu);
		return list;
	}
	
	/**
	 * 获取顶级菜单的第一个菜单id
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTopMenuFirst")
	public String getTopMenuFirst(HttpServletRequest request, Model model, Menu menu) {
		//获取当前用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		user = (User) userService.get(user);
		menu.setRoleId(user.getRoleId());
		//顶级菜单标志为1
		menu.setIsTop("1");
		List<Menu> list = menuService.getMenuTop(menu);
		
		return String.valueOf(list.get(0).getId());
	}
	
	/*初始化菜单方法end*/
	/*----------------------------------------------------------------------------------------------------------------------------------*/
	/*菜单管理方法start*/
	
	/**
	 * 菜单管理列表跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model, Menu menu) {
		//获取当前用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		user = (User) userService.get(user);
		model.addAttribute("user", user);
		return "views/sys/menuList";
	}
	
	
	/**
	 * 获取所有菜单
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dataList")
	public List<Menu> dataList(HttpServletRequest request, Model model, Menu menu) {
		//获取当前用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		user = (User) userService.get(user);
		menu.setRoleId(user.getRoleId());
		
		menu.setIsTop("1");
		List<Menu> list = menuService.getAllList(menu);
		return list;
	}
	
	/**
	 * 获取菜单(树)
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dataJson")
	public List<MenuTree> dataJson(HttpServletRequest request, Model model, MenuTree menu) {
		List<MenuTree> list = menuService.getAllListJson(menu);
		return list;
	}
	
	/**
	 * 获取菜单(树)
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dataCheckJson")
	public List<MenuTreeCheck> dataCheckJson(HttpServletRequest request, Model model, MenuTree menu) {
		String roleId = request.getParameter("roleId");
		menu.setId(1);//默认顶级的id
		List<MenuTreeCheck> list = menuService.getAllListCheckJson(menu, roleId);
		
		return list;
	}
	
	/**
	 * 获取所有菜单
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/data")
	public AjaxResult data(HttpServletRequest request, Model model, Menu menu) {
		//返回实体
		AjaxResult ajaxResult = new AjaxResult();
		try {
			menu.setTitle(menu.getTitle() == null?menu.getTitle():java.net.URLDecoder.decode(menu.getTitle(),"UTF-8"));
			List<Menu> list = menuService.getAllListN(menu);
			ajaxResult.setCode(0);
			ajaxResult.setMsg("");
			ajaxResult.setCount(list.size());
			ajaxResult.setData(list);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return ajaxResult;
	}
	
	
	/**
	 * 菜单管理列表跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/form")
	public String form(HttpServletRequest request, Model model, Menu menu) {
		if(menu.getId() != 0){
			menu = menuService.get(menu);
			Menu temp = new Menu();
			temp.setId(menu.getPid());
			model.addAttribute("menuP",  menuService.get(temp));
		}else if(menu.getPid() == 1){
			Menu temp = new Menu();
			temp.setId(1);
			model.addAttribute("menuP",  menuService.get(temp));
		}else{
			Menu temp = new Menu();
			temp.setId(menu.getPid());
			model.addAttribute("menuP",  menuService.get(temp));
		}
		
		model.addAttribute("menu",  menu);
		return "views/sys/menuForm";
	}
	
	/**
	 * 保存菜单
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, Model model, Menu menu) throws UnsupportedEncodingException {
		String result = "1";//结果标识 1：失败 0：成功
		try
		{
			//设置默认值
			menu.setSpread("0");
			menu.setDelFlag("0");
			
			if(menu.getPid() == 1){
				menu.setIsTop("1");
				menu.setLevel(1);
			}else{
				menu.setIsTop("0");
				Menu temp = new Menu();
				temp.setId(menu.getPid());
				menu.setLevel(menuService.get(temp).getLevel() + 1);
				
			}
			menuService.save(menu);
			result = "0";
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除菜单
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, Model model, Menu menu) {
		String result = "1";
		try {
			menuService.delete(menu.getId());
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 批量删除菜单
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
				menuService.delete(Integer.parseInt(id));
			}
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*菜单管理方法end*/
	
	/**
	 * 图标选择标签（iconselect.tag）
	 */
	@RequestMapping(value = "iconselect")
	public String iconselect(HttpServletRequest request, Model model) {
		return "views/sys/tagIconselect";
	}
}
