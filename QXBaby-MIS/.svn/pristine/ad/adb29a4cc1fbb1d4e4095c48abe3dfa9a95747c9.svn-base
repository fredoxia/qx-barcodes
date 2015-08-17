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
import com.onlineMIS.ORM.entity.headQ.user.UserInfor;
import com.onlineMIS.common.Common_util;
import com.onlineMIS.common.loggerLocal;

public class AccessRightFilterHeadQ implements Filter {

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
	    Object loginUserInforObj = session.getAttribute(Common_util.LOGIN_USER);
	    
	    if (loginUserInforObj != null){
	    	UserInfor loginUserInfor = (UserInfor)loginUserInforObj;
		    String requestURL = req.getRequestURI();
//		    System.out.println(requestURL);
		    String requestFunction = requestURL.substring(requestURL.lastIndexOf("/action/")+8 );
//		    System.out.println(requestFunction);
		    
		    //1. check is it the default function for every one
		    boolean isDefault = DefaultFunctionHeadQ.isDefaultFunction(requestFunction);
		    if (isDefault){
		    	chain.doFilter(request, response);
		    } else {
		    	//2. check whether the user has been granted the high level function
			    boolean exist = loginUserInfor.getFunctions().contains(requestFunction);
			    if (loginUserInfor.getRoleType() != 99 && !exist){
			    	String	originalURL=req.getContextPath() + "/jsp/common/unauthorizedAccess.jsp";
			    	loggerLocal.info("WARNING headq: unauthorized access to " +  requestFunction);
			    	res.sendRedirect(originalURL);
			    } else {
			    	chain.doFilter(request, response);
			    }
		    }
		    		
	    } else {
	    	chain.doFilter(request, response);
	    }

	}

	@Override
	public void init(FilterConfig conf) throws ServletException {
		
      
	}

}
