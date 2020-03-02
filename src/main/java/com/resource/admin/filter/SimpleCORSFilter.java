package com.resource.admin.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "cors")
public class SimpleCORSFilter implements Filter {
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		String originHeader=((HttpServletRequest)req).getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", originHeader);
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Content-Length, Authorization, Accept, X-Requested-With, Referer,User-Agent");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		chain.doFilter(req, res);
	}
	public void init(FilterConfig filterConfig) {}
	public void destroy() {}
}