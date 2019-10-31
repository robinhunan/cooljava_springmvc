package com.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.mapper.sys.MenuMapper;
import com.mapper.user.UserMapper;
import com.model.sys.Menu;
import com.model.user.User;
import com.util.Tool;

public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	UserMapper userService;

	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("[FirstRealm] doGetAuthenticationInfo");
		
		//1. 把 AuthenticationToken 转换为 UsernamePasswordToken 
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		
		//2. 从 UsernamePasswordToken 中来获取 username
		String username = upToken.getUsername();
		String password = new String((char[]) upToken.getPassword());
		//3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
		User user = new User();
		user.setLoginName(username);
		List<User> users = userService.getAllList(user);
		
		System.out.println("从数据库中获取 username: " + username + " 所对应的用户信息.");
		
		//4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
		if(users == null || users.size() == 0){
			throw new UnknownAccountException("用户不存在!");
		}
		User u = users.get(0);
		password = Tool.MD5(password);
		if(!password.equals(u.getPwd())){
			throw new IncorrectCredentialsException("账号或密码不正确!");
		}
		
		//5. 根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常. 
		if(u.getStatus().equals("1")){
			throw new LockedAccountException("用户被锁定");
		}
		
		//3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
		String realmName = getName();
		//4). 盐值. 
		//ByteSource credentialsSalt = ByteSource.Util.bytes(username);
		
		SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
		/*info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);*/
		info = new SimpleAuthenticationInfo(u, u.getPwd(), realmName);
		return info;
	}

	/**
	 * realm授权方法 从输入参数principalCollection得到身份信息 根据身份信息到数据库查找权限信息 将权限信息添加给授权信息对象
	 * 返回 授权信息对象(判断用户访问url是否在权限信息中没有体现)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		User user = (User) principalCollection.getPrimaryPrincipal();
		int roleId = user.getRoleId();//获得角色
		//根据角色获取所有权限
		Menu menu = new Menu();
		menu.setRoleId(roleId);
		
		List<Menu> list = menuMapper.getAllListM(menu);
		digui(list, menu.getRoleId());
		
		// 用户权限列表
		Set<String> permsSet = new HashSet<String>();
		for(Menu m : list){
			if(m.getPermission() != null && !"".equals(m.getPermission())){
				permsSet.add(m.getPermission());
				System.out.println("------------------:"+m.getPermission());
			}
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}
	

	/**
	 * 清理权限缓存
	 */
	public void clearCachedAuthorization()
	{
		// 清空权限缓存
		clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}
	
	/**
	 * 递归获取菜单
	 * @param list
	 * @param roleId
	 */
	public void digui(List<Menu> list, int roleId){
		for(Menu m : list){
			m.setRoleId(roleId);
			List<Menu> listTemp = menuMapper.getByPidM(m);
			if(listTemp.size() > 0){
				m.setChildren(listTemp);
				digui(listTemp, roleId);
			}
		}
		
	}
}
