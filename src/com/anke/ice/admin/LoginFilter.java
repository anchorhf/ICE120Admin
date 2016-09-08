package com.anke.ice.admin;

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

import org.apache.log4j.Logger;

import com.anke.ice.util.LoggerUtil;

public class LoginFilter implements Filter {
	private static final Logger logger = LoggerUtil.getInstance(LoginFilter.class);
	private String filterProcessesUrl = "/j_security_check";
	private String autoPassValue;
	private String NoFilter_Pages;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

		// 获得在下面代码中要用的request,response,session对象
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession();
		String sessionID = session.getId();// session的ID
		// logger.debug("sessionid：" + sessionID);

		// 获得用户请求的URI
		String path = servletRequest.getRequestURI();

		String empId = null;

		//logger.debug("网址：" + path);//测试显示访问网址
		
		// 取Web.xml里面参数NoFilter_Pages写的是无需过滤的页面和函数
		String[] noFilter = NoFilter_Pages.split(",");
		//logger.debug("无需过滤：" + NoFilter_Pages);
		for (int i = 0; i < noFilter.length; i++) {

			// 登陆页面无需过滤
			if (path.indexOf(noFilter[i]) > -1) {

				if (path.indexOf("/api/serchleft/selectleft") > -1) {
					session.setAttribute("sessionID", sessionID);
				}
				chain.doFilter(servletRequest, servletResponse);
				return;
			} else {
				// 从session里取员工工号信息
				empId = (String) session.getAttribute("sessionID");
				// empId = (String) request.getParameter("userName");
			}
		}

		//System.out.println("empId：" + empId);
		// 判断如果没有取到登录人信息,就跳转到登陆页面
		if (empId == null || "".equals(empId)) {
			// 跳转到登陆页面
			java.io.PrintWriter out = response.getWriter();  
		    out.println("<html>");  
		    out.println("<script>");  
		    out.println("window.open ('/login.html','_top')");  
		    out.println("</script>");  
		    out.println("</html>");  
			//servletResponse.sendRedirect("/login.html");
			return;
		} else {
			//System.out.println("empId：ISNOTNULL");
			//if (path.indexOf("login.html") > -1 || path.indexOf("/api/serchleft/selectleft") > -1) {

				//System.out.println("empId：ISOK");
				// 已经登陆,继续此次请求
				chain.doFilter(request, response);
			/*} else {
				System.out.println("empId：ISChuqu");
				// 跳转到登陆页面
				servletResponse.sendRedirect("/main/main.html");
			}*/
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		initParameters(config);
	}

	protected void initParameters(FilterConfig fConfig) {
		/*
		 * if
		 * (StringUtils.isNotBlank(fConfig.getInitParameter("autoPassValue")))
		 * this.autoPassValue = fConfig.getInitParameter("autoPassValue");
		 */

		this.NoFilter_Pages = fConfig.getInitParameter("NoFilter_Pages");
	}

}
