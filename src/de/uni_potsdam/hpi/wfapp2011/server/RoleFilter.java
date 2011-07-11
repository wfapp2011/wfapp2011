package de.uni_potsdam.hpi.wfapp2011.server;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RoleFilter implements Filter{
	
	private boolean debug = true;
	
	//String redirectUrl = "/adminconfig/ConfigurationInterface.html";
	String[] allowedRoles = {"Admin"};
	
	@Override
	public void destroy() {	
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Cookie[] cookies = ((HttpServletRequest)request).getCookies();
		
		Cookie user = null;
		boolean permission = false;
		
		for(Cookie c : cookies){
			if(c.getName().equals("Wfapp2011.USER")){
				if(debug) System.out.println("USER Cookie gefunden: "+ c.getValue());
				user = c;
				break;
			}
		}
		
		if(user == null){
			if(debug) System.out.println("Kein USER-Cookie vorhanden");
			
			permission = false;
		}
		else{
			if(debug) System.out.println("Hole Daten aus der DB");
			
			DbInterface db = new DbInterface();
			db.connectToMetaTables();
	
			String sql = "SELECT username, roles FROM onlineUsers WHERE id="+ user.getValue() +";";
			Collection<Map<String,String>> result = db.executeQuery(sql);
			
			db.disconnect();
			
			if(result == null){
				if(debug) System.out.println("Kein Daten in der DB vorhanden");
				
				permission = false;
			}
			else{
				String[] roles = null;
				
				for(Map<String,String> m : result){
					roles = m.get("roles").replace(" ", "").split(",");
				}
				
				for(String checkAllowedRole : allowedRoles){
					for(String checkRole : roles){
						if(checkAllowedRole.equals(checkRole)){
							if(debug) System.out.println("User hat Zugriffsrechte");
							
							permission = true;
						}
					}
				}
			}
		}
		
		if(permission){
			if(debug) System.out.println("Filter leitet weiter");
			
			chain.doFilter(request, response);
		}
		else{
			if(debug) System.out.println("Filter sperrt");
			SessionManagement.getInstance().logout(Integer.valueOf(user.getValue()));
			
			((HttpServletResponse)response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
