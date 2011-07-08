package de.uni_potsdam.hpi.wfapp2011.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter{
	
	private boolean debug = true;
	
	public void init(FilterConfig arg0) throws ServletException {
		if (debug) System.out.println("Filter initialisiert!");
	}

	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();
		
		if(cookies == null){
			if(debug) System.out.println("Cookieliste == null");
			chain.doFilter(request,response);
		}
		else{
			for(Cookie c : cookies){
				if(c.getName().equals("Wfapp2011_Login_Cookie_TNMS")){
					String username_pwd = c.getValue();
					
					if(debug) System.out.println(username_pwd);
					
					if(SessionManagement.getInstance().isLoggedIn(username_pwd.split("XXX")[0])){
						if(debug) System.out.println("User ist bereits eingeloggt");
						
						chain.doFilter(request, response);
					}
					else{
						if(SessionManagement.getInstance().login(username_pwd.split("XXX")[0], username_pwd.split("XXX")[1])){
							if(debug) System.out.println("Login erfolgreich");
							
							chain.doFilter(request, response);
						}
						else{
							((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
						}
					}
					
				}
			}
		}
	}

	public void destroy() {
	}
}
