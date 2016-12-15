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

public class SecurityFilterChainS implements Filter {

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

		    if (session.getAttribute(Common_util.LOGIN_CHAIN_USER) != null) {

		        chain.doFilter(request, response);
		    } else {
		    	
			    String requestURL = req.getRequestURI();
			    String requestFunction = requestURL.substring(requestURL.lastIndexOf("/"));
			    
                if (requestFunction.equalsIgnoreCase("/purchaseJSONAction!chainInventoryService") ||requestFunction.equalsIgnoreCase("/chainUserJSON!loginService") || requestFunction.equalsIgnoreCase("/chainUserJSON!login") || requestFunction.equalsIgnoreCase("/chainUserJSP!logoff"))
			    	chain.doFilter(request, response);
			    else{
		            res.sendRedirect(req.getContextPath() + "/2.jsp");
		           
			    }
		    }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
