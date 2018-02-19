package com.wethink.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.wethink.data.controller.UserProfile;
import com.wethink.data.model.ResourceInfo;

public class ResourceFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		UserProfile profile = UserProfile.getInstance();
		ResourceInfo resInfo = new ResourceInfo(1000, 1, 0);
		profile.setInfo(resInfo);
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub 
		
	}

}
