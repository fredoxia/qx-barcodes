package com.onlineMIS.filter;

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

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.onlineMIS.common.Common_util;

public class SecurityFilterHeadQ implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		    HttpServletRequest req = (HttpServletRequest) request;
		    HttpServletResponse res = (HttpServletResponse) response;
		    HttpSession session = req.getSession();

		    if (session.getAttribute(Common_util.LOGIN_USER) != null) {

		        chain.doFilter(request, response);
		    } else {
		    	
			    String requestURL = req.getRequestURI();
			    String requestFunction = requestURL.substring(requestURL.lastIndexOf("/") + 1);
			    
			    if (requestFunction.equalsIgnoreCase("loginJSON!login4Chain") || requestFunction.equalsIgnoreCase("loginJSON!login") || requestFunction.equalsIgnoreCase("userJSON!checkSession") || requestFunction.equalsIgnoreCase("loginJSON!reLogin") || requestFunction.equalsIgnoreCase("loginJSON!PDALogin") || requestFunction.equalsIgnoreCase("userJSP!logoff4Chain") || requestFunction.equalsIgnoreCase("ipadJSP!logoffPDA"))
			    	chain.doFilter(request, response);
			    else{
			    	
			    	if (requestFunction.toLowerCase().endsWith("pda")){
			    		res.sendRedirect(req.getContextPath() + "/4.jsp");
			    	} else if (requestFunction.contains("ipad") || requestURL.contains("ipad"))
			    		res.sendRedirect(req.getContextPath() + "/indexIPAD.jsp");
			    	else 
		                res.sendRedirect(req.getContextPath() + "/1.jsp");
		           
			    }
		    }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
