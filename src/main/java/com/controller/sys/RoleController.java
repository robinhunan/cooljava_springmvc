package com.controller.sys;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.model.sys.Role;
import com.service.sys.RoleService;
import com.shiro.ShiroRealm;

@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	/**
	 * 角色管理跳转
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model) {
		return "views/sys/roleList";
	}
	
	/**
	 * 角色数据
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/data")
	public Map<String, Object> data(HttpServletRequest request, Model model, Role role) throws UnsupportedEncodingException {
		
		List<Role> list = roleService.getListByPage(role);
        Long count = roleService.getCount(role);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", count);
        map.put("data", list);

        return map;
	}
	
	/**
	 * 新增角色
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/form")
	public String form(Role role, HttpServletRequest request, Model model) {
		role = roleService.get(role);
		model.addAttribute("role", role);
		return "views/sys/roleForm";
	}
	
	/**
	 * 保存角色信息
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		 Role role = new  Role();
		 role.setName(request.getParameter("name"));
		 String id = request.getParameter("id");
		 if(id != null && id != ""){
			 role.setId(Integer.parseInt(id));
		 }
		try {
			roleService.save(role);
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			return "1";
		}
		
	}
	
	/**
	 * 删除角色
	 * @param request
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public String delete(HttpServletRequest request, Model model, Role role) {
		String result = "1";
		try {
			roleService.delete(role.getId());
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
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping(value = "/setUse")
	public String setUse(HttpServletRequest request, Model model, Role role) {
		String result = "1";
		role = roleService.get(role);
		try {
			if(role.getUseable().equals("0")){
				role.setUseable("1");
			}else{
				role.setUseable("0");
			}
			roleService.save(role);
			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 权限设置跳转页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/setPermission")
	public String setPermission(Role role, HttpServletRequest request, Model model) {
		role = roleService.get(role);
		model.addAttribute("role", role);
		return "views/sys/permission";
	}
	
	/**
	 * 保存权限设置
	 * @param request
	 * @param model
	 * @param roleId
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/savePermission")
	public String savePermission(HttpServletRequest request, Model model, String roleId, String ids) {
		String result = "1";
		try {
			roleService.savePermission(roleId, ids);
			//保存权限后刷新当前用户权限
			RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
			ShiroRealm realm = (ShiroRealm)rsm.getRealms().iterator().next(); 
		    realm.clearCachedAuthorization();

			result = "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
