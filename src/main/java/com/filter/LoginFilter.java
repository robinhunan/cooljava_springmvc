package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.user.User;

public class LoginFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 获得在下面代码中要用的request,response,session对象
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession();
		StringBuffer server = servletRequest.getRequestURL();
		 
		// 获得用户请求的URI
		String path = servletRequest.getRequestURI();

		User user = (User) session.getAttribute("user");

		// 登陆页面无需过滤
		if (path.indexOf("/login/index.do") > -1) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}
		 if(server.toString().contains(".css") || server.toString().contains(".js") || server.toString().contains(".png")){
        //如果发现是css或者js文件，直接放行
			chain.doFilter(servletRequest, servletResponse);
            return;
        }
		// 判断如果没有取到员工信息,就跳转到登陆页面
		if (user == null  && !(path.indexOf("/login/login.do") > -1)) {
			// 跳转到登陆页面
			servletResponse.sendRedirect(servletRequest.getContextPath() + "/login/index.do");
		} else {
			// 已经登陆,继续此次请求
			chain.doFilter(servletRequest, servletResponse);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}